document.addEventListener('DOMContentLoaded', () => {
    const gameModeSelect = document.getElementById('gameMode');
    const boardSizeSelect = document.getElementById('boardSize');
    const startBtn = document.getElementById('startBtn');
    const gameContainer = document.getElementById('gameContainer');
    const boardElement = document.getElementById('board');
    const statusElement = document.getElementById('status');
    const modal = document.getElementById('modal');
    const modalTitle = document.getElementById('modalTitle');
    const restartBtn = document.getElementById('restartBtn');

    let boardSize = 3;
    let vsComputer = true;
    let currentPlayer = 'X';
    let gameActive = false;

    startBtn.addEventListener('click', startGame);
    restartBtn.addEventListener('click', startGame);

    function startGame() {
        boardSize = parseInt(boardSizeSelect.value);
        vsComputer = gameModeSelect.value === '1';

        // Reset UI
        boardElement.innerHTML = '';
        boardElement.className = 'board';
        boardElement.classList.add(`size-${boardSize}`);
        statusElement.textContent = `Player X's turn`;
        modal.classList.add('hidden');

        // Create board cells
        for (let i = 0; i < boardSize; i++) {
            for (let j = 0; j < boardSize; j++) {
                const cell = document.createElement('div');
                cell.classList.add('cell');
                cell.dataset.row = i;
                cell.dataset.col = j;
                cell.addEventListener('click', handleCellClick);
                boardElement.appendChild(cell);
            }
        }

        gameContainer.classList.remove('hidden');

        // Initialize backend game
        fetch('/api/game/start', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                boardSize: boardSize,
                vsComputer: vsComputer
            })
        })
        .then(response => {
            if (!response.ok) throw new Error('Failed to start game');
            gameActive = true;
            currentPlayer = 'X';
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to start game');
        });
    }

    function handleCellClick(event) {
        if (!gameActive) return;

        const cell = event.target;
        const row = parseInt(cell.dataset.row);
        const col = parseInt(cell.dataset.col);

        if (cell.textContent !== '') return;

        makeMove(row, col, cell);
    }

    function makeMove(row, col, cellElement = null) {
        fetch('/api/game/move', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                row: row,
                col: col,
                player: currentPlayer
            })
        })
        .then(response => {
            if (!response.ok) throw new Error('Move failed');
            return response.json();
        })
        .then(gameState => {
            if (cellElement && cellElement.textContent === '') {
                cellElement.textContent = currentPlayer;
            }

            updateCurrentPlayerUI(gameState.currentPlayer);
            checkGameState(gameState);

            if (vsComputer && gameState.status === 'IN_PROGRESS' && gameState.currentPlayer === 'O') {
                makeComputerMove();
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Move failed. Please try again.');
        });
    }

    function updateCurrentPlayerUI(player) {
        currentPlayer = player;
        statusElement.textContent = `Player ${currentPlayer}'s turn`;
    }

    function makeComputerMove() {
        fetch('/api/game/computer-move')
        .then(response => {
            if (!response.ok) throw new Error('Failed to get computer move');
            return response.json();
        })
        .then(([row, col]) => {
            if (row !== -1 && col !== -1) {
                const cell = document.querySelector(`.cell[data-row="${row}"][data-col="${col}"]`);
                setTimeout(() => {
                    makeMove(row, col, cell);
                }, 400); // delay for natural feel
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
    }

    function checkGameState(gameState) {
        if (gameState.status === 'IN_PROGRESS') return;

        gameActive = false;

        let message = '';
        switch (gameState.status) {
            case 'PLAYER_X_WON':
                message = 'Player X wins!';
                break;
            case 'PLAYER_O_WON':
                message = 'Player O wins!';
                break;
            case 'DRAW':
                message = "It's a draw!";
                break;
        }

        modalTitle.textContent = message;
        modal.classList.remove('hidden');
    }
});
