<?php      
require 'config.php';

if (isset($_POST['add'])) {   // ++ variable 
    $id = $_POST['id'];
    $codep = $_POST['code'];
    $date = $_POST['date'];
    $num = $_POST['num'];
    $desc = $_POST['desc'];
   
    $conn = new config();
    $pdo = $conn::getConnexion();
    $query = "INSERT INTO remboursement (codep, id, date_achat, numero, description) VALUES (?, ?, ?, ?, ?)"; 

    $stmt = $pdo->prepare($query);
    $stmt->bindParam(1, $codep);
    $stmt->bindParam(2, $id);
    $stmt->bindParam(3, $date);
    $stmt->bindParam(4, $num);
    $stmt->bindParam(5, $desc);

    // Execute the statement
    $stmt->execute();
}
?>

<!DOCTYPE html>
<html lang="en" class="no-js">
    <head>
        <!--- basic page needs
        ================================================== -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>RecyTech</title>
    
        <script>
            document.documentElement.classList.remove('no-js');
            document.documentElement.classList.add('js');
        </script>
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700" rel="stylesheet">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css" integrity="sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz" crossorigin="anonymous">
        <!-- CSS
        ================================================== -->
        <link rel="stylesheet" href="css/vendor.css">
        <link rel="stylesheet" href="css/styles.css">
        <link rel="stylesheet" href="css/style.css">


    
        <!-- favicons
        ================================================== -->
        <link rel="icon" type="image/png" sizes="16x16" href="images/favicon.jpg">
        <link rel="manifest" href="site.webmanifest">

        <include type="text/html" data="index.html">
        <include type="text/html" data="profil.html">


    
    </head>

    <body id="top" class="ss-show">
        <div id="preloader" style="display: none;">
            <div id="loader">
            </div>
        </div>
    <div id="page" class="s-pagewrap">

     <header class="s-header">

        <div class="row s-header__inner">

            <div class="s-header__block">
                <div class="s-header__logo">
                    <a class="logo" href="index.html">
                        <img src="images/logo.svg" alt="Homepage">  
                    </a>
                </div>

                <a class="s-header__menu-toggle" href="#0"><span>Menu</span></a>
            </div> <!-- end s-header__block -->

            <nav class="s-header__nav">
                <ul>
                    <li class="current"><a href="index.html#intro" class="smoothscroll">Intro</a></li>
                    <li><a href="index.html#about" class="smoothscroll">About</a></li>
                    <li><a href="index.html#pricing" class="smoothscroll">Pricing</a></li>
                    <li><a href="index.html#download" class="smoothscroll">Blog</a></li>
                    <li><a href="formulaire.php" class="smoothscroll">Refund</a></li>
                </ul>
                
            </nav>

            

        </div> <!-- end s-header__inner -->

     </header>
     <section id="content" class="s-content">
        <section id="intro" class="s-intro target-section">

            <div class="s-intro__bg"></div>

            <div class="row s-intro__content">

                <div class="column lg-12 s-intro__content-inner">
                    <div class="s-intro__content-left">
                        <h1 class="s-intro__content-title">
                            You will  <br>
                            Love Your Bin.
                        </h1>
                    </div>
                    <div class="s-intro__content-right">
                        <p class="s-intro__content-desc body-text-2">
                            We get rid of your trash and then <br>
                            We turn it into treasures<br>
                            </p>

                        <div class="s-intro__content-buttons">
                            <a href="./shop.html" class="btn btn--primary s-intro__content-btn smoothscroll">SHOP NOW</a>
                            <a href="https://player.vimeo.com/video/414263504?color=00a650&amp;title=0&amp;byline=0&amp;portrait=0" class="s-intro__content-video-btn">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" style="fill: rgba(0, 0, 0, 1);transform: msFilter;"><path d="M7 6v12l10-6z"></path></svg>
                            </a>
                        </div>
                    </div>
                </div> <!-- s-intro__content-inner -->

            </div> <!-- s-intro__content -->
            <ul class="s-intro__social">
                <li>
                    <a href="">
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" style="fill:rgba(0, 0, 0, 1);transform:-ms-filter"><path d="M20,3H4C3.447,3,3,3.448,3,4v16c0,0.552,0.447,1,1,1h8.615v-6.96h-2.338v-2.725h2.338v-2c0-2.325,1.42-3.592,3.5-3.592 c0.699-0.002,1.399,0.034,2.095,0.107v2.42h-1.435c-1.128,0-1.348,0.538-1.348,1.325v1.735h2.697l-0.35,2.725h-2.348V21H20 c0.553,0,1-0.448,1-1V4C21,3.448,20.553,3,20,3z"></path></svg>
                        <span class="u-screen-reader-text">Facebook</span>
                    </a>
                </li>
                <li>
                    <a href="">
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" style="fill:rgba(0, 0, 0, 1);transform:-ms-filter"><path d="M19.633,7.997c0.013,0.175,0.013,0.349,0.013,0.523c0,5.325-4.053,11.461-11.46,11.461c-2.282,0-4.402-0.661-6.186-1.809 c0.324,0.037,0.636,0.05,0.973,0.05c1.883,0,3.616-0.636,5.001-1.721c-1.771-0.037-3.255-1.197-3.767-2.793 c0.249,0.037,0.499,0.062,0.761,0.062c0.361,0,0.724-0.05,1.061-0.137c-1.847-0.374-3.23-1.995-3.23-3.953v-0.05 c0.537,0.299,1.16,0.486,1.82,0.511C3.534,9.419,2.823,8.184,2.823,6.787c0-0.748,0.199-1.434,0.548-2.032 c1.983,2.443,4.964,4.04,8.306,4.215c-0.062-0.3-0.1-0.611-0.1-0.923c0-2.22,1.796-4.028,4.028-4.028 c1.16,0,2.207,0.486,2.943,1.272c0.91-0.175,1.782-0.512,2.556-0.973c-0.299,0.935-0.936,1.721-1.771,2.22 c0.811-0.088,1.597-0.312,2.319-0.624C21.104,6.712,20.419,7.423,19.633,7.997z"></path></svg>
                        <span class="u-screen-reader-text">Twitter</span>
                    </a>
                </li>
                <li>
                    <a href="">
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" style="fill:rgba(0, 0, 0, 1);transform:-ms-filter"><path d="M11.999,7.377c-2.554,0-4.623,2.07-4.623,4.623c0,2.554,2.069,4.624,4.623,4.624c2.552,0,4.623-2.07,4.623-4.624 C16.622,9.447,14.551,7.377,11.999,7.377L11.999,7.377z M11.999,15.004c-1.659,0-3.004-1.345-3.004-3.003 c0-1.659,1.345-3.003,3.004-3.003s3.002,1.344,3.002,3.003C15.001,13.659,13.658,15.004,11.999,15.004L11.999,15.004z"></path><circle cx="16.806" cy="7.207" r="1.078"></circle><path d="M20.533,6.111c-0.469-1.209-1.424-2.165-2.633-2.632c-0.699-0.263-1.438-0.404-2.186-0.42 c-0.963-0.042-1.268-0.054-3.71-0.054s-2.755,0-3.71,0.054C7.548,3.074,6.809,3.215,6.11,3.479C4.9,3.946,3.945,4.902,3.477,6.111 c-0.263,0.7-0.404,1.438-0.419,2.186c-0.043,0.962-0.056,1.267-0.056,3.71c0,2.442,0,2.753,0.056,3.71 c0.015,0.748,0.156,1.486,0.419,2.187c0.469,1.208,1.424,2.164,2.634,2.632c0.696,0.272,1.435,0.426,2.185,0.45 c0.963,0.042,1.268,0.055,3.71,0.055s2.755,0,3.71-0.055c0.747-0.015,1.486-0.157,2.186-0.419c1.209-0.469,2.164-1.424,2.633-2.633 c0.263-0.7,0.404-1.438,0.419-2.186c0.043-0.962,0.056-1.267,0.056-3.71s0-2.753-0.056-3.71C20.941,7.57,20.801,6.819,20.533,6.111z M19.315,15.643c-0.007,0.576-0.111,1.147-0.311,1.688c-0.305,0.787-0.926,1.409-1.712,1.711c-0.535,0.199-1.099,0.303-1.67,0.311 c-0.95,0.044-1.218,0.055-3.654,0.055c-2.438,0-2.687,0-3.655-0.055c-0.569-0.007-1.135-0.112-1.669-0.311 c-0.789-0.301-1.414-0.923-1.719-1.711c-0.196-0.534-0.302-1.099-0.311-1.669c-0.043-0.95-0.053-1.218-0.053-3.654 c0-2.437,0-2.686,0.053-3.655c0.007-0.576,0.111-1.146,0.311-1.687c0.305-0.789,0.93-1.41,1.719-1.712 c0.534-0.198,1.1-0.303,1.669-0.311c0.951-0.043,1.218-0.055,3.655-0.055c2.437,0,2.687,0,3.654,0.055 c0.571,0.007,1.135,0.112,1.67,0.311c0.786,0.303,1.407,0.925,1.712,1.712c0.196,0.534,0.302,1.099,0.311,1.669 c0.043,0.951,0.054,1.218,0.054,3.655c0,2.436,0,2.698-0.043,3.654H19.315z"></path></svg>
                        <span class="u-screen-reader-text">Instagram</span>
                    </a>
                </li>
            </ul>
            
            <div class="s-intro__scroll">
                <a href="#about" class="smoothscroll">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" style="fill: rgba(0, 0, 0, 1);transform: rotate(270deg);-msFilter:progid:DXImageTransform.Microsoft.BasicImage(rotation=3);"><path d="M21 11H6.414l5.293-5.293-1.414-1.414L2.586 12l7.707 7.707 1.414-1.414L6.414 13H21z"></path></svg>
                    <span class="u-screen-reader-text">Scroll</span>
                </a>
            </div> <!-- s-intro__scroll -->

        </section>
     </section>
     <div class="main">

        <section class="signup" id="subscribe">
        <div class="container">
        <div class="signup-content">
        <div class="signup-form">
        <h2 class="form-title">Instant Refund</h2>
        <form method="POST" class="register-form" >

        <div class="form-group" >
        <label for="name" id="icon"><i class="fas fa-user"></i></label>
        <input type="text" name="code" id="code" placeholder="Product code"  class="no-outline"/>
        <input type="text" name="id" id="id" placeholder="Identy card "  class="no-outline"/>
        <input type="text" name="date" id="date" placeholder="date of purchase"  class="no-outline"/>
        <input type="text" name="num" id="num" placeholder="Phone number"  class="no-outline"/>
        <input type="text" name="desc" id="desc" placeholder="Description"  class="no-outline"/>
        </div>
<!--
        <div class="form-group" >
        <label for="email" id="icon"><i class="fas fa-envelope" class="control-label"></i></label>
        <input type="text"  name="email" id="email" placeholder="Your Email" class="no-outline"/>
        </div>

        <div class="form-group">
        <label for="pass" id="icon"><i class="fas fa-unlock-alt"></i></label>
        <input type="password" name="pass" id="pass" placeholder="Password"  class="no-outline"/>
        </div>

        <div class="form-group">
        <label for="re-pass" id="icon"><i class="fas fa-unlock-alt"></i></label>
        <input type="password" name="re_pass" id="re_pass" placeholder="Repeat your password"  class="no-outline"/>
        </div>

        <div class="form-group">
        <label for="re-pass" id="icon"><i class="fas fa-unlock-alt"></i></label>
        <input type="password" name="re_pass" id="re_pass" placeholder="Repeat your password"  class="no-outline"/>
        </div>
--> 
        <div class="form-group">
            <div class="form-group">
            </div>
        </div>

        <div class="btn-block">
            <a href="./profil.html"><button type="submit" name="add" id="add">Add</button></a>
          </div>
        </form>
        </div>

        <div class="signup-image">
         <figure><img src="images/signup-image.png" alt="sing up image"></figure>
    
        </div>
        </div>
        </div>
        </section>
        
        </div>
     <footer class="s-footer footer">
        <div class="row s-footer__bottom">
            <div class="column lg-5 md-6 stack-on-900">
                <div class="footer__logo">
                    <a href="index.html">
                        <img src="images/logo.svg" alt="Homepage">
                    </a>
                </div>

                <p>
                Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed 
                do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
                Ut enim ad minim veniam, quis nostrud exercitation ullamco 
                laboris nisi ut.
                </p>

                <ul class="s-footer__social">
                    <li>
                        <a href="">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" style="fill:rgba(0, 0, 0, 1);transform:-ms-filter;"><path d="M20,3H4C3.447,3,3,3.448,3,4v16c0,0.552,0.447,1,1,1h8.615v-6.96h-2.338v-2.725h2.338v-2c0-2.325,1.42-3.592,3.5-3.592 c0.699-0.002,1.399,0.034,2.095,0.107v2.42h-1.435c-1.128,0-1.348,0.538-1.348,1.325v1.735h2.697l-0.35,2.725h-2.348V21H20 c0.553,0,1-0.448,1-1V4C21,3.448,20.553,3,20,3z"></path></svg>
                            <span class="u-screen-reader-text">Facebook</span>
                        </a>
                    </li>
                    <li>
                        <a href="">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" style="fill:rgba(0, 0, 0, 1);transform:-ms-filter;"><path d="M19.633,7.997c0.013,0.175,0.013,0.349,0.013,0.523c0,5.325-4.053,11.461-11.46,11.461c-2.282,0-4.402-0.661-6.186-1.809 c0.324,0.037,0.636,0.05,0.973,0.05c1.883,0,3.616-0.636,5.001-1.721c-1.771-0.037-3.255-1.197-3.767-2.793 c0.249,0.037,0.499,0.062,0.761,0.062c0.361,0,0.724-0.05,1.061-0.137c-1.847-0.374-3.23-1.995-3.23-3.953v-0.05 c0.537,0.299,1.16,0.486,1.82,0.511C3.534,9.419,2.823,8.184,2.823,6.787c0-0.748,0.199-1.434,0.548-2.032 c1.983,2.443,4.964,4.04,8.306,4.215c-0.062-0.3-0.1-0.611-0.1-0.923c0-2.22,1.796-4.028,4.028-4.028 c1.16,0,2.207,0.486,2.943,1.272c0.91-0.175,1.782-0.512,2.556-0.973c-0.299,0.935-0.936,1.721-1.771,2.22 c0.811-0.088,1.597-0.312,2.319-0.624C21.104,6.712,20.419,7.423,19.633,7.997z"></path></svg>
                            <span class="u-screen-reader-text">Twitter</span>
                        </a>
                    </li>
                    <li>
                        <a href="">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" style="fill:rgba(0, 0, 0, 1);transform:-ms-filter;"><path d="M11.999,7.377c-2.554,0-4.623,2.07-4.623,4.623c0,2.554,2.069,4.624,4.623,4.624c2.552,0,4.623-2.07,4.623-4.624 C16.622,9.447,14.551,7.377,11.999,7.377L11.999,7.377z M11.999,15.004c-1.659,0-3.004-1.345-3.004-3.003 c0-1.659,1.345-3.003,3.004-3.003s3.002,1.344,3.002,3.003C15.001,13.659,13.658,15.004,11.999,15.004L11.999,15.004z"></path><circle cx="16.806" cy="7.207" r="1.078"></circle><path d="M20.533,6.111c-0.469-1.209-1.424-2.165-2.633-2.632c-0.699-0.263-1.438-0.404-2.186-0.42 c-0.963-0.042-1.268-0.054-3.71-0.054s-2.755,0-3.71,0.054C7.548,3.074,6.809,3.215,6.11,3.479C4.9,3.946,3.945,4.902,3.477,6.111 c-0.263,0.7-0.404,1.438-0.419,2.186c-0.043,0.962-0.056,1.267-0.056,3.71c0,2.442,0,2.753,0.056,3.71 c0.015,0.748,0.156,1.486,0.419,2.187c0.469,1.208,1.424,2.164,2.634,2.632c0.696,0.272,1.435,0.426,2.185,0.45 c0.963,0.042,1.268,0.055,3.71,0.055s2.755,0,3.71-0.055c0.747-0.015,1.486-0.157,2.186-0.419c1.209-0.469,2.164-1.424,2.633-2.633 c0.263-0.7,0.404-1.438,0.419-2.186c0.043-0.962,0.056-1.267,0.056-3.71s0-2.753-0.056-3.71C20.941,7.57,20.801,6.819,20.533,6.111z M19.315,15.643c-0.007,0.576-0.111,1.147-0.311,1.688c-0.305,0.787-0.926,1.409-1.712,1.711c-0.535,0.199-1.099,0.303-1.67,0.311 c-0.95,0.044-1.218,0.055-3.654,0.055c-2.438,0-2.687,0-3.655-0.055c-0.569-0.007-1.135-0.112-1.669-0.311 c-0.789-0.301-1.414-0.923-1.719-1.711c-0.196-0.534-0.302-1.099-0.311-1.669c-0.043-0.95-0.053-1.218-0.053-3.654 c0-2.437,0-2.686,0.053-3.655c0.007-0.576,0.111-1.146,0.311-1.687c0.305-0.789,0.93-1.41,1.719-1.712 c0.534-0.198,1.1-0.303,1.669-0.311c0.951-0.043,1.218-0.055,3.655-0.055c2.437,0,2.687,0,3.654,0.055 c0.571,0.007,1.135,0.112,1.67,0.311c0.786,0.303,1.407,0.925,1.712,1.712c0.196,0.534,0.302,1.099,0.311,1.669 c0.043,0.951,0.054,1.218,0.054,3.655c0,2.436,0,2.698-0.043,3.654H19.315z"></path></svg>
                            <span class="u-screen-reader-text">Instagram</span>
                        </a>
                    </li>
                </ul>
            </div>

            <div class="column lg-6 stack-on-900 end">
                <ul class="s-footer__site-links">
                    <li><a class="smoothscroll" href="index.html#into" title="intro">Intro</a></li>
                    <li><a class="smoothscroll" href="index.html#about" title="about">About</a></li>
                    <li><a class="smoothscroll" href="index.html#testimonials" title="reviews">Reviews</a></li>
                    <li><a class="smoothscroll" href="index.html#pricing" title="pricing">Pricing</a></li>
                    <li><a class="smoothscroll" href="index.html#download" title="Blog">Blog</a></li>	
                </ul>

                <p class="s-footer__contact">
                    Do you have a question? Send us a word: <br>
                    <a href="mailto:#0" class="s-footer__mail-link">support@RecyTech.com</a>
                </p>

                <div class="ss-copyright">
                    <span>© RecyTech 2021</span> 
                    <span>Designed by <a href="https://www.RecyTech.com/">RecyTech</a></span>
                </div>
            </div>

        </div>

        <div class="ss-go-top">
            <a class="smoothscroll" title="Back to Top" href="#top">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" style="fill: rgba(0, 0, 0, 1);transform: msFilter;"><path d="M6 4h12v2H6zm5 10v6h2v-6h5l-6-6-6 6z"></path></svg>
            </a>
        </div> <!-- end ss-go-top -->

    </footer> <!-- end footer -->
    <script src="js/jquery.min.js"></script>
    <script src="js/main.js"></script>
    </div>
    </body>
    </html>
