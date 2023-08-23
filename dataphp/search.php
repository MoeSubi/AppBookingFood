<?php
include "connect.php";
$search = isset($_POST['search']) ? $_POST['search'] : '';
if (empty($search)) {
  $arr = [
            'success' => false,
            'message' => "khong thanh cong",
        ];
} else {
$query = "SELECT * FROM `food_detail` WHERE `food_name`LIKE '%". $search."%'";

$data = mysqli_query($conn, $query);
if (!$data) {
    echo "Lỗi truy vấn: " . mysqli_error($conn);
    // Hoặc ghi log lỗi, xử lý lỗi phù hợp...
} else {
    $result = array();
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
            'message' => "Sản phẩm bạn tìm không có !",
            'result' => $result
        ];
    }
  }
}
    print_r(json_encode($arr));
?>
