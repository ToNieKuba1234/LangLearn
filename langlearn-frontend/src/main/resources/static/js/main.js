document.getElementById("trigger-btn").addEventListener("click", () => {

    fetch('localhost:3000/api/translate', {
        method: 'POST'
    });

    console.log("DEBUG: POST is sent!")
});