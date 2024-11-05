<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AJAX with XMLHttpRequest</title>
    <style>
        #result {
            margin-top: 20px;
            padding: 10px;
            border: 1px solid #ddd;
            background-color: #f9f9f9;
        }
        .error {
            color: red;
        }
    </style>
</head>
<body>
    <h1>AJAX Example</h1>
    <form id="data-form">
        <label for="query">Enter Query:</label>
        <input type="text" id="query" name="query">
        <button type="submit">Submit</button>
    </form>
    <div id="result"></div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const form = document.getElementById('data-form');
            const resultDiv = document.getElementById('result');

            form.addEventListener('submit', function(event) {
                event.preventDefault(); // Prevent form from submitting the traditional way
                
                const query = document.getElementById('query').value;
                
                if (query) {
                    const xhr = new XMLHttpRequest();
                    xhr.open('GET', `server.php?query=${encodeURIComponent(query)}`, true);

                    xhr.onload = function() {
                        if (xhr.status >= 200 && xhr.status < 300) {
                            // Success response
                            resultDiv.innerHTML = `<p>Response: ${xhr.responseText}</p>`;
                        } else {
                            // Error response
                            resultDiv.innerHTML = `<p class="error">Error: ${xhr.status} - ${xhr.statusText}</p>`;
                        }
                    };

                    xhr.onerror = function() {
                        resultDiv.innerHTML = `<p class="error">Request failed. Please try again.</p>`;
                    };

                    xhr.send();
                } else {
                    resultDiv.innerHTML = '<p class="error">Please enter a query.</p>';
                }
            });
        });
    </script>
</body>
</html>
