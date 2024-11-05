function loadXMLDoc() {
    // Create a new XMLHttpRequest object
    var xhr = new XMLHttpRequest();

    // Configure it: GET-request for the URL rec.txt
    xhr.open('GET', 'rec.txt', true);

    // Set up a function to handle the response when it arrives
    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300) {
            // Successful response
            document.getElementById('content').textContent = xhr.responseText;
        } else {
            // Handle errors if needed
            document.getElementById('content').textContent = 'Failed to load content.';
        }
    };

    // Handle network errors
    xhr.onerror = function () {
        document.getElementById('content').textContent = 'Request failed.';
    };

    // Send the request
    xhr.send();
}
