<?php
$conexion = mysql_connect("localhost", "root", "root");
mysql_select_db("tutorial", $conexion);
mysql_query("SET NAMES 'utf8'");

$chipid = $_POST ['chipid'];
$temperatura = $_POST ['temperatura'];

mysql_query("INSERT INTO `tutorial`.`tabla` (`id`, `chipId`, `fecha`, `temperatura`) VALUES (NULL, '$chipid', CURRENT_TIMESTAMP, '$temperatura');");

mysql_close();

echo "Datos ingresados correctamente.";
?>
