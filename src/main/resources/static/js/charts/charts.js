function verticalBarChart(element) {

    return new Chart(element.getContext('2d'), {
        type: 'bar',
        data: {
            labels: ["Red", "Blue", "Yellow", "Green", "Purple", "Orange"],
            datasets: [{
                label: '# of Votes',
                data: [12, 19, 3, 5, 2, 3],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                    'rgba(255, 159, 64, 0.2)'
                ],
                borderColor: [
                    'rgba(255,99,132,1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 159, 64, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero:true
                    }
                }]
            }
        }
    });

}

function lineChart(element) {

    return new Chart(element, {
        type: 'line',
        data: {
            labels: [1500,1600,1700,1750,1800,1850,1900,1950,1999,2050],
            datasets: [{
                data: [86,114,106,106,107,111,133,221,783,2478],
                label: "Africa",
                borderColor: "#3e95cd",
                fill: false
            }, {
                data: [282,350,411,502,635,809,947,1402,3700,5267],
                label: "Asia",
                borderColor: "#8e5ea2",
                fill: false
            }, {
                data: [168,170,178,190,203,276,408,547,675,734],
                label: "Europe",
                borderColor: "#3cba9f",
                fill: false
            }, {
                data: [40,20,10,16,24,38,74,167,508,784],
                label: "Latin America",
                borderColor: "#e8c3b9",
                fill: false
            }, {
                data: [6,3,2,2,7,26,82,172,312,433],
                label: "North America",
                borderColor: "#c45850",
                fill: false
            }
            ]
        },
        options: {
            title: {
                display: true,
                text: 'World population per region (in millions)'
            }
        }
    });

}

function pieChart(element) {

    new Chart(element, {
        type: 'pie',
        data: {
            labels: ["Africa", "Asia", "Europe", "Latin America", "North America"],
            datasets: [{
                label: "Population (millions)",
                backgroundColor: ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850"],
                data: [2478,5267,734,784,433]
            }]
        },
        options: {
            title: {
                display: true,
                text: 'Predicted world population (millions) in 2050'
            }
        }
    });

}

function doughnutChart(element) {

    new Chart(element, {
        type: 'doughnut',
        data: {
            labels: ["Finalizados", "Total"],
            datasets: [
                {
                    backgroundColor: ["#3e95cd", "#ffffff"],
                    data: [1511, 35]
                }
            ]
        },
        options: {
            cutoutPercentage: 85,
            title: {
                display: true,
                text: 'Finalizados'
            }
        }
    });

}

function halfDoughnutChart(element, data, title) {

    new Chart(element, {
        type: 'doughnut',
        data: data,
        options: {
            rotation: 1 * Math.PI,
            circumference: 1 * Math.PI,
            cutoutPercentage: 70,
            legend: {
                display: false
            },
            title: {
                display: true,
                text: title
            }
        }
    });

}

function groupedBarChart(element, data) {

    new Chart(element, {
        type: 'bar',
        data: data,
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }],
                xAxes: [{
                    gridLines: {
                        display: false
                    }
                }]
            }
        }
    });
}

function mixedChart(element) {

    new Chart(element, {
        type: 'bar',
        data: {
            labels: ["1900", "1950", "1999", "2050"],
            datasets: [{
                label: "Europe",
                type: "line",
                borderColor: "#8e5ea2",
                data: [408,547,675,734],
                fill: false
            }, {
                label: "Africa",
                type: "line",
                borderColor: "#3e95cd",
                data: [133,221,783,2478],
                fill: false
            }, {
                label: "Europe",
                type: "bar",
                backgroundColor: "rgba(0,0,0,0.2)",
                data: [408,547,675,734],
            }, {
                label: "Africa",
                type: "bar",
                backgroundColor: "rgba(0,0,0,0.2)",
                backgroundColorHover: "#3e95cd",
                data: [133,221,783,2478]
            }
            ]
        },
        options: {
            title: {
                display: true,
                text: 'Population growth (millions): Europe & Africa'
            },
            legend: { display: false }
        }
    });

}

function progressBar(element, color, porcentagem) {

    var bar = new ProgressBar.Circle(element, {
        strokeWidth: 5,
        easing: 'easeInOut',
        duration: 500,
        color: color,
        trailColor: '#b6b6b6',
        trailWidth: 1,
        svgStyle: null
    });

    bar.animate(porcentagem);

    return bar
}

function progressCircle(element, colorObject, percentage) {

    var progressCircle = new ProgressBar.Circle(element, {
        strokeWidth: 9,
        color: colorObject.color,
        trailWidth: 9,
        trailColor: colorObject.disabledColor,
        easing: 'easeInOut',
        duration: 1400,
        strokeLinecap: 'round',
        step: (state, bar) => {
            bar.setText(Math.round(bar.value() * 100) + '%');
        },
        text: {
            style: {
                position: 'absolute',
                left: '50%',
                top: '50%',
                padding: '0px',
                margin: '0px',
                transform: 'translate(-50%, -50%)',
                fontSize: '18px',
                fontWeight: '600'
            }
        }
    });

    progressCircle.animate(percentage);
    return progressCircle;
}

function progressSemiCicle(element, colorObject, percentage, absolutNumber) {

    var progressSemiCircle = new ProgressBar.SemiCircle(element, {
        strokeWidth: 15,
        color: colorObject.color,
        trailWidth: 15,
        trailColor: colorObject.disabledColor,
        easing: 'easeInOut',
        duration: 1400,
        strokeLinecap: 'round',
        step: (state, bar) => {
            var text = '<span style="font-size: 12px; font-weight: 500; color: #2E2D2C">Faltam</span><br>' + absolutNumber;
            bar.setText(text);
        },
        text: {
            style: {
                position: 'absolute',
                left: '50%',
                color: 'rgb(0, 185, 142)',
                fontSize: '18px',
                fontWeight: '600',
                lineHeight: '1'
            }
        }
    });

    progressSemiCircle.animate(percentage);
    return progressSemiCircle;
}

function colorObject(r, g, b) {
    var color = `rgba(${r}, ${g}, ${b}, 1)`;
    var disabledColor = `rgba(${r}, ${g}, ${b}, 0.1)`;
    return { color, disabledColor }
}