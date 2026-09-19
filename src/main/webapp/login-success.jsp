<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Successful</title>

<style>
body{
    margin:0;
    font-family:Arial;
    background:#f5f5f5;
    text-align:center;
}

.header{
    background:#232f3e;
    color:white;
    padding:20px;
    font-size:28px;
    font-weight:bold;
}

.box{
    background:white;
    width:420px;
    max-width:85%;
    margin:100px auto;
    padding:40px;
    border-radius:15px;
    box-shadow:0 5px 20px #ccc;
    animation:pop .6s ease;
}

@keyframes pop{
    from{transform:scale(.5);opacity:0}
    to{transform:scale(1);opacity:1}
}

.icon{
    font-size:60px;
}

h1{
    color:#16803c;
}

h2{
    color:#232f3e;
}

p{
    color:#555;
    font-size:17px;
}

.btn{
    display:inline-block;
    margin-top:20px;
    padding:14px 28px;
    background:#ffd814;
    color:#111;
    text-decoration:none;
    border-radius:25px;
    font-weight:bold;
}

.confetti{
    position:fixed;
    top:-20px;
    width:10px;
    height:18px;
    animation:fall 3s linear forwards;
}

@keyframes fall{
    to{
        transform:translateY(110vh) rotate(720deg);
        opacity:0;
    }
}
</style>
</head>

<body>

<div class="header">🛒 Deepa Mart</div>

<div class="box">
    <div class="icon">🎉</div>

    <h1>Login Successful!</h1>

    <h2>Welcome to Deepa Mart</h2>

    <p>
    Welcome,
    <b><%= esc(String.valueOf(session.getAttribute("username"))) %></b>!
</p>
    <p>Happy Shopping! 🛒</p>

    <a href="products" class="btn">
        Start Shopping 🛍️
    </a>
</div>

<script>
window.onload=function(){

    let colors=[
        "#ff4757","#1e90ff","#2ed573",
        "#ffa502","#a55eea","#00cec9"
    ];

    for(let i=0;i<70;i++){

        let c=document.createElement("div");

        c.className="confetti";
        c.style.left=Math.random()*100+"vw";
        c.style.backgroundColor=
            colors[Math.floor(Math.random()*colors.length)];
        c.style.animationDelay=Math.random()*1.5+"s";

        document.body.appendChild(c);

        setTimeout(()=>c.remove(),5000);
    }
};
</script>

</body>
<%!
public String esc(String s) {
    if (s == null) return "";
    return s.replace("&","&amp;")
            .replace("<","&lt;")
            .replace(">","&gt;")
            .replace("\"","&quot;")
            .replace("'","&#39;");
}
%>
</html>