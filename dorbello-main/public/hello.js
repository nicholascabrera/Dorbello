document.addEventListener("DOMContentLoaded", function() {
    fetch("http://localhost:8080/info/test", {
        method: "GET"
    })
    .then(response => response.json())
    .then(data => {
        const jDataElement = document.querySelector('.jData');
        jDataElement.textContent += JSON.stringify(data, null, 2);
        console.log(data);
    })
    .catch(error => console.error('Error:', error));
});
