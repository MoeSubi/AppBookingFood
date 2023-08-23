<?php
include "connect.php";
$query = "SELECT * FROM `user` ORDER BY user_id DESC";
$data = mysqli_query($conn, $query);

if (!$data) {
  $error = mysqli_error($conn);
  $arr = [
    'success' => false,
    'message' => "Query execution failed: $error",
    'result' => []
  ];
} else {
  $result = [];
  while ($row = mysqli_fetch_assoc($data)) {
    $result[] = $row;
  }

  if (!empty($result)) {
    $arr = [
      'success' => true,
      'message' => "Get all the users successful!!!",
      'result' => $result
    ];
  } else {
    $arr = [
      'success' => false,
      'message' => "Get all the users unsuccessful!!!",
      'result' => $result
    ];
  }
}

print_r(json_encode($arr));
?>
