<?php
include "connect.php";
$user_id = isset($_POST['user_id']) ? (int)$_POST['user_id'] : 0;
$id_role = isset($_POST['id_role']) ? (int)$_POST['id_role'] : 0;

if ($user_id === 0 && $id_role === 1) {
    // User có user_id = 0 và id_role = 1, cho phép truy cập tất cả đơn hàng
    $query = 'SELECT order_info.id_order, order_info.address, order_info.phone_number, order_info.email, order_info.size, order_info.total, order_info.date_created, user.full_name FROM `order_info` INNER JOIN user ON order_info.user_id = user.user_id ORDER BY order_info.id_order DESC';
} else {
    $query = 'SELECT order_info.id_order, order_info.address, order_info.phone_number, order_info.email, order_info.size, order_info.total, order_info.date_created, user.full_name FROM `order_info` INNER JOIN user ON order_info.user_id = user.user_id WHERE order_info.user_id = ? ORDER BY order_info.id_order DESC';
}

$stmt = mysqli_prepare($conn, $query);

if ($stmt) {
    if ($user_id !== 0) {
        mysqli_stmt_bind_param($stmt, "i", $user_id);
    }

    mysqli_stmt_execute($stmt);
    $data = mysqli_stmt_get_result($stmt);

    $result = array();

    while ($row = mysqli_fetch_assoc($data)) {
        $truyvan = 'SELECT * FROM `order_detail` INNER JOIN food_detail ON order_detail.id_detail = food_detail.id_detail WHERE order_detail.id_order_info = ?';
        $stmt1 = mysqli_prepare($conn, $truyvan);

        if ($stmt1) {
            mysqli_stmt_bind_param($stmt1, "i", $row['id_order']);
            mysqli_stmt_execute($stmt1);
            $data1 = mysqli_stmt_get_result($stmt1);

            $item = array();
            while ($row1 = mysqli_fetch_assoc($data1)) {
                $item[] = $row1;
            }

            $row['item'] = $item;
            $result[] = $row;

            mysqli_stmt_close($stmt1);
        }
        if ($row['date_created'] == '0000-00-00') {
            $row['date_created'] = null;
        }
    }

    mysqli_stmt_close($stmt);
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
?>
