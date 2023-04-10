
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Admin - Bootstrap 5 Admin Template</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="keywords">
    <meta content="" name="description">

    <!-- Favicon -->
    <link href="img/favicon.ico" rel="icon">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Roboto:wght@500;700&display=swap" rel="stylesheet"> 
    
    <!-- Icon Font Stylesheet -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />

    <!-- Customized Bootstrap Stylesheet -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="css/style.css" rel="stylesheet">
</head>
<body>
    <div class="container-fluid position-relative d-flex p-0">
      

        <!-- Sidebar Start -->
        <div class="sidebar pe-4 pb-3">
            <nav class="navbar bg-secondary navbar-dark">
                <a href="index.html" class="navbar-brand mx-4 mb-3">
                    <h3 class="text-primary"><i class="fa fa-user-edit me-2"></i>Recytech</h3>
                </a>
                <div class="d-flex align-items-center ms-4 mb-4">
                    <div class="position-relative">
                        <img class="rounded-circle" src="img/user.jpg" alt="" style="width: 40px; height: 40px;">
                        <div class="bg-success rounded-circle border border-2 border-white position-absolute end-0 bottom-0 p-1"></div>
                    </div>
                    <div class="ms-3">
                        <h6 class="mb-0">Admin</h6>
                    </div>
                </div>
                <div class="navbar-nav w-100">
                    <a href="index.html" class="nav-item nav-link"><i class="fa fa-tachometer-alt me-2"></i>Admin Panel</a>
                    <div class="nav-item dropdown">
                      <a href="#" class="nav-link dropdown-toggle " data-bs-toggle="dropdown"><i class="fa fa-laptop me-2"></i>Shop</a>
                        <div class="dropdown-menu bg-transparent border-0">
                            <a href="Add_product.html" class="dropdown-item ">Add Products</a>
                            <a href="typography.html" class="dropdown-item">View Orders</a>
                        </div>  
                    </div>
                    <a href="form.html" class="nav-item nav-link"><i class="fa fa-keyboard me-2"></i>schedulling</a>
                    <a href="widget.html" class="nav-item nav-link"><i class="fa fa-th me-2"></i>Donation</a>
                    <div class="nav-item dropdown">
                        <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"><i class="fa fa-laptop me-2"></i>Blog</a>
                          <div class="dropdown-menu bg-transparent border-0">
                              <a href="button.html" class="dropdown-item">Add Blog</a>
                              <a href="typography.html" class="dropdown-item">Manage Comments</a>
                          </div>  
                      </div>

                    <div class="nav-item dropdown">
                        <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"><i class="fa fa-laptop me-2"></i>Recyclage</a>
                          <div class="dropdown-menu bg-transparent border-0">
                          <a href="recyclage.php" class="dropdown-item active ">Remboursement</a>
                              <a href=".php" class="dropdown-item">Produit à Recycler</a>
                          </div>  
                      </div>
                    
                </div>
            </nav>
        </div>
        <!-- Sidebar End -->

                    <?php
require '../front/config.php'; // Include the config.php file

// Call the getConnexion() method from the config class to establish a database connection
$pdo = config::getConnexion();

// Prepare and execute the query to fetch remboursement from the database
$get_remboursement = $pdo->prepare("SELECT * FROM `remboursement`");
$get_remboursement->execute();

// Fetch the data from the query result
$remboursement = $get_remboursement->fetchAll();

?>

<!-- Content Start -->
<div class="content">
    <!-- Products -->
    <div class="container-fluid pt-4 px-4">
        <div class="bg-secondary text-center rounded p-4">
            <div class="table-responsive">
                <table class="table text-start align-middle table-bordered table-hover mb-0">
                    <thead>
                        <tr class="text-white">
                            <th scope="col">Product Code</th>
                            <th scope="col">Id</th>
                            <th scope="col">Purchase date</th>
                            <th scope="col">Phone Number</th>
                            <th scope="col">Description</th>

                        </tr>
                    </thead>
                    <tbody>
                        <?php
                        // Loop through the fetched products and display them in the table
                        foreach ($remboursement as $item) {
                            echo "<tr>";
                            echo "<td>{$item['codep']}</td>";
                            echo "<td>{$item['id']}</td>";
                            echo "<td>{$item['date_achat']}</td>";
                            echo "<td>{$item['numero']}</td>";
                            echo "<td>{$item['description']}</td>";
                            echo "<td>";
                            echo "<form method='POST' action=''>";
                            echo "<input type='hidden' name='id' value='{$item['id']}'>";
                            echo "<button type='submit' name='delete' class='btn btn-danger btn-sm'><i class='fa fa-trash'></i> Delete</button>";
                            echo "</form>";
                            echo "<a href='edit_precycler.php?id={$item['id']}' class='btn btn-warning btn-sm'><i class='fa fa-edit'></i> Edit</a>";
                            echo "</td>";
                            echo "</tr>";
                        }
                        ?>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <!-- Products -->
</div>
<!-- Content End -->





        <!-- Back to Top -->
        <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="bi bi-arrow-up"></i></a>
    </div>

    <!-- JavaScript Libraries -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="lib/chart/chart.min.js"></script>
    <script src="lib/easing/easing.min.js"></script>
    <script src="lib/waypoints/waypoints.min.js"></script>
    <script src="lib/owlcarousel/owl.carousel.min.js"></script>
    <script src="lib/tempusdominus/js/moment.min.js"></script>
    <script src="lib/tempusdominus/js/moment-timezone.min.js"></script>
    <script src="lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>

    <!-- Template Javascript -->
    <script src="js/main.js"></script>
</body>

</html>