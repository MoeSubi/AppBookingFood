<?php
include "connect.php";
$page = isset($_POST['page']) ? $_POST['page'] : 1;
$total = 5;
$pos = ($page - 1) * $total;
$id_cate = isset($_POST['id_cate']) ? $_POST['id_cate'] : '';

$query = 'SELECT * FROM `food_detail` WHERE `id_cate` = \'' . $id_cate . '\' LIMIT ' . $pos . ',' . $total;

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
            'message' => "khong thanh cong",
            'result' => $result
        ];
    }

    print_r(json_encode($arr));
}
?>
