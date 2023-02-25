'use strict';

const modal = document.querySelector('.modal');
const overlay = document.querySelector('.overlay');
const close = document.querySelector('.close-modal');
const openModal = () => {
    modal.classList.remove('hidden');
    overlay.classList.remove('hidden');
}

const closeModal = () => {
    modal.classList.add('hidden');
    overlay.classList.add('hidden');
}
document.addEventListener('keydown', (event) => {
    const key = event.key;
    if (key === 'Escape' && !modal.classList.contains('hidden')) {
        closeModal();
    }
})