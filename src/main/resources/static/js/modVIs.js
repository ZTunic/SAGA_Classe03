function modificaVisibilitaVeicolo(){

    let array = document.getElementsByClassName("campiVis");
    for(let i=0; i<array.length; i++){
        if(array[i].readOnly == true){
            array[i].readOnly= false;
            array[i].style.border="1px solid black";
            document.getElementById("modificaVeicolo").classList.add("d-none");
            document.getElementById("annullaModificaVeicolo").classList.remove("d-none");
            document.getElementById("salvaModificheVeicolo").style.display = "inline";
            document.getElementById("resetModificheVeicolo").style.display = "inline";
        }
        else{
            array[i].readOnly= true;
            array[i].style.border="none";
            document.getElementById("modificaVeicolo").classList.remove("d-none");
            document.getElementById("annullaModificaVeicolo").classList.add("d-none");
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
            document.getElementById("modificaProfilo").classList.add("d-none");
            document.getElementById("annullaModificaProfilo").classList.remove("d-none");
            document.getElementById("salvaModificheProfilo").style.display = "inline";
            document.getElementById("resetModificheProfilo").style.display = "inline";
        }
        else{
            array[i].readOnly= true;
            array[i].style.border="none";
            document.getElementById("modificaProfilo").classList.remove("d-none");
            document.getElementById("annullaModificaProfilo").classList.add("d-none");
            document.getElementById("salvaModificheProfilo").style.display = "none";
            document.getElementById("resetModificheProfilo").style.display = "none";
        }
    }
}

function attivaFormVeicolo(){
    let form = document.getElementById("containerFormVeicolo");

    if(form.style.display == 'none') {
        form.style.display = 'block';
        document.getElementById("aggiuntaVeicolo").classList.add("d-none");
        document.getElementById("annullaAggiuntaVeicolo").classList.remove("d-none");
        document.getElementById("salvaModificheVeicolo").style.display = "inline";
        document.getElementById("resetModificheVeicolo").style.display = "inline";
    }
    else {
        form.style.display = 'none';
        document.getElementById("aggiuntaVeicolo").classList.remove("d-none");
        document.getElementById("annullaAggiuntaVeicolo").classList.add("d-none");
        document.getElementById("salvaModificheVeicolo").style.display = "none";
        document.getElementById("resetModificheVeicolo").style.display = "none";
    }
}