<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

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

    <div id="menu">
        <tiles:insertAttribute name="menu" />
    </div>

    <div id="conteudo">
        <tiles:insertAttribute name="conteudo" />
    </div>
    
    <div id="rodape">
        <tiles:insertAttribute name="rodape" />
    </div>
</div>
</body>
</html>
