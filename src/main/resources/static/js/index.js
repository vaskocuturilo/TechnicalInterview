'use strict';

const modal = document.querySelector('.modal');
const overlay = document.querySelector('.overlay');
const close = document.querySelector('.close-modal');
const openModal = function () {
    modal.classList.remove('hidden');
    overlay.classList.remove('hidden');
}

const closeModal = () => {
    modal.classList.add('hidden');
    overlay.classList.add('hidden');
}

close.addEventListener('click', closeModal);
overlay.addEventListener('click',closeModal);
document.addEventListener('keydown', (event) => {
    const key = event.key;
    if (key === 'Escape' && !modal.classList.contains('hidden')) {
        closeModal();
    }
})