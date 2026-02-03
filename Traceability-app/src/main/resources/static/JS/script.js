const sessionId = [[${sessionId}]];
const inputField = document.getElementById('textInput');
const container = document.getElementById('container');
let counter = 1;
inputField.addEventListener('keypress', (e) => {
    if (e.key === 'Enter') {
        e.preventDefault(); // sprečava da forma uradi submit ako postoji

        const value = inputField.value.trim();
        if (!value) return;

        if(value.startsWith("RF")){
            newDiv.classList.add("pcs-rf")
        }else if(value.startsWith("KK")) {
            newDiv.classList.add("pcs-kk")
            openModal();
        } else if (value.startsWith("RUS")){
            newDiv.classList.add("pcs-rus")
        }else if (value.startsWith("KS")){
            newDiv.classList.add("pcs-ks")
        }else if(value.startsWith("RK")){
            fetch('/piece/add-ok', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json'},
                body: JSON.stringify({
                    qrCode: value,
                    sessionId: sessionId})
            })
            .then(response => response.json())
            .then(data => {
                if(data.saved){
                    createDiv(value);

                }else{
                    alert("Already in Database")
                }
            });



        // newDiv.textContent += value;
        // newDiv.classList.add('pcs')
        // container.appendChild(newDiv);

        // // 2. Pošalji ka backendu
        // fetch('/api/add-item', {
        //     method: 'POST',
        //     headers: { 'Content-Type': 'application/json' },
        //     body: JSON.stringify({ content: value })
        // });


        // 3. Očisti input

        inputField.value = '';
    }
}});


function createDiv(value){

    const newDiv = document.createElement('div');
    newDiv.textContent=counter+" > ";
    newDiv.textContent += value;
        newDiv.classList.add('pcs')
        container.appendChild(newDiv);
        counter++;
}


function openModal() {
document.getElementById("myModal").style.display = "block";
}

function closeModal() {
    document.getElementById("myModal").style.display = "none";
}

