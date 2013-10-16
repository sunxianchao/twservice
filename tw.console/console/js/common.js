function closedialog(param) {
	// 当关闭对话框时刷新当前页面
	navTabPageBreak();
	return true;
}

function query4Date(btn) {
	var sd = getDateStr(-1);
	var ed = getDateStr(0);
	if (btn == 2) {// 上周
		var d = new Date();
		sd = getDateStr(-(d.getDay() + 7));
		ed = getDateStr(-(d.getDay()) - 1);
	}
	if (btn == 3) {// 上月
		var dd = new Date();
		var y = dd.getFullYear();
		var m = dd.getMonth();// 获取当前月份的日期
		if (m == 0)
			m = 1;
		if (m < 10)
			m = "0" + m;
		var sd = y + "-" + m + "-01";
		var ed = y + "-" + m + "-31";
	}
	$("#startDate").val(sd);
	$("#endDate").val(ed);
}

function getDateStr(addDayCount) {
	var dd = new Date();
	dd.setDate(dd.getDate() + addDayCount);// 获取AddDayCount天后的日期
	var y = dd.getFullYear();
	var m = dd.getMonth() + 1;// 获取当前月份的日期
	var d = dd.getDate();
	if (m < 10)
		m = "0" + m;
	if (d < 10)
		d = "0" + d;
	return y + "-" + m + "-" + d;
}