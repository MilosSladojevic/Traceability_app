const inputField = document.getElementById('textInput');
const container = document.getElementById('container');
let counter = 1;
inputField.addEventListener('keypress', (e) => {
    if (e.key === 'Enter') {
        e.preventDefault(); // sprečava da forma uradi submit ako postoji

        const value = inputField.value.trim();
        if (!value) return;

        // 1. Napravi novi div sa tekstom
        const newDiv = document.createElement('div');
        newDiv.textContent=counter+" > ";
        if(value.startsWith("RF")){
            newDiv.classList.add("pcs-rf")
        }else if(value.startsWith("KK")) {
            newDiv.classList.add("pcs-kk")
        } else if (value.startsWith("RUS")){
            newDiv.classList.add("pcs-rus")
        }else if (value.startsWith("KS")){
            newDiv.classList.add("pcs-ks")
        }
        newDiv.textContent += value;
        newDiv.classList.add('pcs')
        container.appendChild(newDiv);

        // 2. Pošalji ka backendu
//        fetch('/api/add-item', {
//            method: 'POST',
//            headers: { 'Content-Type': 'application/json' },
//            body: JSON.stringify({ content: value })
//        });


        // 3. Očisti input
        counter++;
        inputField.value = '';
    }
});