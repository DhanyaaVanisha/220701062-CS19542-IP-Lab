<?php
header('Content-Type: text/plain');

// Simulate processing of the query
if (isset($_GET['query'])) {
    $query = htmlspecialchars($_GET['query']);
    // For demonstration, simply echo the query back
    echo "You submitted: " . $query;
} else {
    echo "No query received.";
}
?>
