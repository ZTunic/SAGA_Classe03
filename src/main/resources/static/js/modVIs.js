function modificaVisibilitaVeicolo(){

    let array = document.getElementsByClassName("campiVis");
    for(let i=0; i<array.length;i++){
        if(array[i].readOnly == true){
        array[i].readOnly= false;
        array[i].style.border="1px solid black";
        }
        else{
            array[i].readOnly= true;
            array[i].style.border="none";
        }
    }
}

function modificaVisibilitaProfilo(){

    let array = document.getElementsByClassName("campiProfVis");
    for(let i=0; i<array.length;i++){
        if(array[i].readOnly == true){
            array[i].readOnly= false;
            array[i].style.border="1px solid black";
        }
        else{
            array[i].readOnly= true;
            array[i].style.border="none";
        }
    }
}