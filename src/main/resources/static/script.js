// Funzione per ottenere i dati dall'endpoint
async function fetchData(url) {
    const response = await fetch(url);
    const data = await response.json();
    return data;
}

// Funzione per ottenere il nome del giorno della settimana
function getDayName(dateStr) {
    const date = new Date(dateStr);
    const options = { weekday: 'long' };
    return new Intl.DateTimeFormat('it-IT', options).format(date);
}

// Funzione per ottenere la data corrente in formato YYYY-MM-DD
function getCurrentDate() {
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0'); // I mesi in JavaScript sono 0-indexed
    const day = String(today.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

// Funzione per creare il grafico per i dati giornalieri
async function createDailyChart() {
    const data = await fetchData("counter/totaleUltimi14Giorni");
    
    // Estrai le etichette (giorni) e i dati (numeroEventi)
    const labels = data.map(item => {
        const dayName = getDayName(item.giorno);
        return `${dayName} (${item.giorno})`;
    });
    const numeroEventi = data.map(item => item.numeroEventi);
    const macchineAlMinuto = data.map(item => item.macchineAlMinuto);

    const ctx = document.getElementById('myChart').getContext('2d');
    new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [
                {
                    label: 'Numero Macchine',
                    data: numeroEventi,
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1,
                    fill: false,
                    yAxisID: 'y1'
                },
                {
                    label: 'Macchine al Minuto',
                    data: macchineAlMinuto,
                    borderColor: 'rgba(255, 99, 132, 1)',
                    borderWidth: 1,
                    fill: false,
                    yAxisID: 'y2'
                }
            ]
        },
        options: {
            responsive: true,
            scales: {
                x: {
                    display: true,
                    title: {
                        display: true,
                        text: 'Giorni'
                    }
                },
                y1: {
                    display: true,
                    title: {
                        display: true,
                        text: 'Numero Macchine'
                    }
                },
                y2: {
                    display: true,
                    position: 'right',
                    title: {
                        display: true,
                        text: 'Macchine al Minuto'
                    }
                }
            },
            plugins: {
                datalabels: {
                    display: true,
                    align: 'top',
                    color: 'black',
                    formatter: function(value, context) {
                        return value;
                    }
                },
                tooltip: {
                    mode: 'index',
                    intersect: false
                },
                hover: {
                    mode: 'nearest',
                    intersect: true
                }
            }
        },
        plugins: [ChartDataLabels]
    });
}

let hourlyChart; // Variabile per memorizzare l'istanza del grafico

// Funzione per creare o aggiornare il grafico per i dati orari
async function createOrUpdateHourlyChart(date) {
    const data = await fetchData(`counter/eventiPerOra?data=${date}`);
    
    // Estrai le etichette (ore) e i dati (numeroEventi)
    const labels = data.map(item => item.ora);
    const numeroEventi = data.map(item => item.numeroEventi);

    const ctx = document.getElementById('myChart2').getContext('2d');
    
    if (hourlyChart) {
        hourlyChart.destroy(); // Distrugge il grafico esistente prima di crearne uno nuovo
    }

    hourlyChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [
                {
                    label: 'Numero Macchine',
                    data: numeroEventi,
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                }
            ]
        },
        options: {
            responsive: true,
            scales: {
                x: {
                    display: true,
                    title: {
                        display: true,
                        text: 'Ore'
                    }
                },
                y: {
                    display: true,
                    title: {
                        display: true,
                        text: 'Numero Macchine'
                    }
                }
            },
            plugins: {
                datalabels: {
                    display: true,
                    align: 'top',
                    color: 'black',
                    formatter: function(value, context) {
                        return value;
                    }
                },
                title: {
                    display: true,
                    text: `Eventi per Ora del Giorno ${date}`
                }
            }
        },
        plugins: [ChartDataLabels]
    });
}

// Funzione per aggiornare il grafico hourlyChart in base alla data selezionata
function updateHourlyChart() {
    const datePicker = document.getElementById('datePicker');
    const selectedDate = datePicker.value;
    if (selectedDate) {
        createOrUpdateHourlyChart(selectedDate);
    } else {
        alert('Seleziona una data valida');
    }
}

// Funzione per ottenere e mostrare il numero di macchine del giorno corrente
async function fetchCurrentDayData() {
    const currentDate = getCurrentDate();
    const data = await fetchData(`counter/eventiPerGiorno?data=${currentDate}`);
    return data;
}

async function displayCurrentDayData() {
    const data = await fetchCurrentDayData();
    const currentDayElement = document.getElementById('currentDayCount');
    currentDayElement.textContent = `Numero macchine di oggi: ${data.numeroEventi}`;
}

// Chiamata alle funzioni per creare i grafici e mostrare i dati quando la pagina è pronta
document.addEventListener('DOMContentLoaded', (event) => {
    createDailyChart();
    createOrUpdateHourlyChart(getCurrentDate());
    displayCurrentDayData();
});
