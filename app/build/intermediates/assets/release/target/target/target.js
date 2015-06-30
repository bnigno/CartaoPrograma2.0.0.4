	function load_canv_1(){
		var canvas = document.getElementById("_canv_1");

		if (canvas && canvas.getContext){
			var ctx = canvas.getContext("2d");
			ctx.fillStyle = "rgba(255,255,255,1)";
			ctx.beginPath();
			ctx.moveTo(-.00000461,-.00006472);
			ctx.lineTo(793.71,-.00006472);
			ctx.lineTo(793.71,1122.52);
			ctx.lineTo(-.00000461,1122.52);
			ctx.lineTo(-.00000461,-.00006472);
			ctx.closePath();
			ctx.fill();
		}
	}
	
function DrawPage1(){
	load_canv_1();
}
