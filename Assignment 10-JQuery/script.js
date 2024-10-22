$(document).ready(function () {
    // Screen resolution width and height minus some pixels for browser space
    var width = screen.width - 100;
    var height = screen.height - 200;

    // Function to generate random alphabet between A - Z
    function getRandomLetter() {
        var code = Math.floor(Math.random() * 26) + 65; // Generate random number between 65 and 90
        return String.fromCharCode(code); // Convert key code to corresponding character
    }

    // Function to generate random color
    function getRandomColor() {
        var letters = '0123456789ABCDEF';
        var color = '#';
        for (var i = 0; i < 6; i++) {
            color += letters[Math.floor(Math.random() * 16)];
        }
        return color;
    }

    // Function to create and display the bubble
    function createBubble(letter) {
        // Create a new div for the bubble
        var $bubble = $('<div class="bubble"></div>').text(letter);

        // Set random position within screen bounds
        var randomLeft = Math.floor(Math.random() * (width - 50));
        var randomTop = Math.floor(Math.random() * (height - 50));
        $bubble.css({
            left: randomLeft + 'px',
            top: randomTop + 'px',
            backgroundColor: getRandomColor()
        });

        // Append the bubble to the game area
        $('#game-area').append($bubble);

        // Animate bubble floating upwards and then removing it
        $bubble.animate({
            top: "-=800px"
        }, 3000, function () {
            $(this).remove(); // Remove bubble after animation
        });
    }

    // Event listener for key press
    $(document).keypress(function (e) {
        var keyCode = e.which || e.keyCode;

        // Check if the key code is between A-Z (65-90)
        if (keyCode >= 65 && keyCode <= 90) {
            var letter = String.fromCharCode(keyCode).toUpperCase(); // Convert key code to letter
            createBubble(letter); // Generate a bubble with that letter
        }
    });
});
