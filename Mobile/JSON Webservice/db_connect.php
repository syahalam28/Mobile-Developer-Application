<?php 
	
	define('DB_USER', "trepisco_biodata"); // db user
	define('DB_PASSWORD', "biodata12345"); // db password (mention your db password here)
	define('DB_DATABASE', "trepisco_biodata"); // database name
	define('DB_SERVER', "valerion"); // db server
	 
	$con = mysqli_connect(DB_SERVER,DB_USER,DB_PASSWORD,DB_DATABASE);	
	 
	// Check connection
	if(mysqli_connect_errno()){
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}

 ?>