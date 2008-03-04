function checkit(e) {
    var bgcheck = "#DDDDCC";
    var bguncheck = "#ffffff";  
    if (e.checked == true) {
        while (e.tagName != "TR") {
            e = e.parentNode;
        }
        e.bgColor = bgcheck;
    } else {
        while (e.tagName != "TR") {
            e = e.parentNode;
        }
        e.bgColor = bguncheck;
    }
}

function allselect(e,name) {
    var form = e.form;
    var bgcheck = "#DDDDCC";
    var bguncheck = "#ffffff";
    var boxes;
    if (form.allcheck.checked == true) {
        for (var loop = 0;loop < form.elements.length;loop++) {
            if (form.elements[loop].name == name) {
                form.elements[loop].checked = true ; 
                boxes = form.elements[loop];
                while (boxes.tagName != "TR") {
                    boxes = boxes.parentNode;
                }
                boxes.bgColor = bgcheck;
            }   
        }
    } else {
        for (var loop = 0;loop < form.elements.length;loop++) {
            if (form.elements[loop].name == name) {
                form.elements[loop].checked = false ;
                boxes = form.elements[loop];
                while (boxes.tagName != "TR") {
                    boxes = boxes.parentNode;
                }
                boxes.bgColor = bguncheck;
            }
        }
    }

}