function modificaVisibilitaVeicolo(){

    let array = document.getElementsByClassName("campiVis");
    for(let i=0; i<array.length; i++){
        if(array[i].readOnly == true){
            array[i].readOnly= false;
            array[i].style.border="1px solid black";
            document.getElementById("testoModificaVeicolo").innerHTML = "<i class=\"fa fa-close\"</i>&nbsp Chiudi pannello modifica";
            document.getElementById("salvaModificheVeicolo").style.display = "inline";
            document.getElementById("resetModificheVeicolo").style.display = "inline";
        }
        else{
            array[i].readOnly= true;
            array[i].style.border="none";
            document.getElementById("testoModificaVeicolo").innerHTML = "<i class=\"bi bi-pencil\" aria-hidden=\"true\"></i>&nbsp Modifica profilo";
            document.getElementById("salvaModificheVeicolo").style.display = "none";
            document.getElementById("resetModificheVeicolo").style.display = "none";
        }
    }
}

function modificaVisibilitaProfilo(){

    let array = document.getElementsByClassName("campiProfVis");
    for(let i=0; i<array.length; i++){
        if(array[i].readOnly == true){
            array[i].readOnly= false;
            array[i].style.border="1px solid black";
            document.getElementById("testoModificaProfilo").innerHTML = "<i class=\"fa fa-close\"</i>&nbsp Chiudi pannello modifica";
            document.getElementById("salvaModificheProfilo").style.display = "inline";
            document.getElementById("resetModificheProfilo").style.display = "inline";
        }
        else{
            array[i].readOnly= true;
            array[i].style.border="none";
            document.getElementById("testoModificaProfilo").innerHTML = "<i class=\"bi bi-pencil\" aria-hidden=\"true\"></i>&nbsp Modifica profilo";
            document.getElementById("salvaModificheProfilo").style.display = "none";
            document.getElementById("resetModificheProfilo").style.display = "none";
        }
    }
}

function attivaFormVeicolo(){
    let x = document.getElementById("containerFormVeicolo");

    if(x.style.display == 'none'){
        x.style.display = 'inline';
        document.getElementById("salvaModificheVeicolo").style.display = "inline";
        document.getElementById("resetModificheVeicolo").style.display = "inline";
    }
    else{
        x.style.display = 'none';
        document.getElementById("salvaModificheVeicolo").style.display = "none";
        document.getElementById("resetModificheVeicolo").style.display = "none";
    }
}