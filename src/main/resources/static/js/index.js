let slidesNum = 4;
let startingSlide = 2;
let currentSlide = startingSlide;
let previousSlide = startingSlide -1

setInterval(slideshow, 5000);

function slideshow() {
    if (currentSlide > slidesNum)
        currentSlide = 1;
    if(previousSlide > slidesNum)
        previousSlide = 1;
    let previousImgId = "background-image" + previousSlide;
    let currentImgId = "background-image" + currentSlide;
    document.getElementById(previousImgId).classList.remove("d-block");
    document.getElementById(previousImgId).classList.add("d-none");
    document.getElementById(currentImgId).classList.remove("d-none");
    document.getElementById(currentImgId).classList.add("d-block");
    previousSlide++;
    currentSlide++;
}