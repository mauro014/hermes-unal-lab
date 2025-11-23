days = new Array(7)
    days[1] = "";
    days[2] = "";
    days[3] = ""; 
    days[4] = "";
    days[5] = "";
    days[6] = "";
    days[7] = "";
    months = new Array(12)
    months[1] = "enero";
    months[2] = "febrero";
    months[3] = "marzo";
    months[4] = "abril";
    months[5] = "mayo";
    months[6] = "junio";
    months[7] = "julio";
    months[8] = "agosto";
    months[9] = "septiembre";
    months[10] = "octubre"; 
    months[11] = "noviembre";
    months[12] = "diciembre";
    today = new Date(); day = days[today.getDay() + 1]
    month = months[today.getMonth() + 1]
    date = today.getDate()
    year=today.getYear(); 
if (year < 2000)
year = year + 1900;
    document.write (day +
    " " + date + " de " + month + " de " + year)