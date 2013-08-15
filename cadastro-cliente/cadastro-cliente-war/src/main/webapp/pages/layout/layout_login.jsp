<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!doctype html>
<html lang="pt-br">
<head>
<meta charset="utf-8">

<!-- viewport meta to reset iPhone inital scale -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title><tiles:insertAttribute name="titulo" ignore="true" /></title>

<!-- css3-mediaqueries.js for IE8 or older -->
<!--[if lt IE 9]>
    <script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script>
<![endif]-->

<tiles:insertAttribute name="imports" />

</head>

<body>

<div id="areatotal">

    <div id="cabecalho">
		<tiles:insertAttribute name="cabecalho" />
    </div>

    <tiles:insertAttribute name="mensagens" />

    <tiles:insertAttribute name="conteudo" />
    
    <tiles:insertAttribute name="conteudo_baixo" />
    
    <div id="rodape">
       <tiles:insertAttribute name="rodape" />
    </div>

</div>

</body>
</html>
