<?php
include "connect.php";
$query = "SELECT order_detail.id_detail, food_detail.food_name, COUNT(`size`) AS Tong FROM `order_detail` INNER JOIN food_detail ON food_detail.id_detail = order_detail.id_detail GROUP BY order_detail.id_detail";
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
      'message' => "thanh cong",
      'result' => $result
    ];
  } else {
    $arr = [
      'success' => false,
      'message' => "khong thanh cong",
      'result' => $result
    ];
  }
}

print_r(json_encode($arr));
?>
