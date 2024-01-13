document.addEventListener('DOMContentLoaded', function () {

    var adviceSwitch = document.getElementById('adviceSwitch');
    var adviceText = document.querySelector('.advice-text');

    adviceSwitch.addEventListener('change', function () {
        adviceText.style.display = this.checked ? 'block' : 'none';
    });
});