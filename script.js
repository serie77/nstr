// Loading screen and main content management
document.addEventListener('DOMContentLoaded', function() {
    const loadingScreen = document.getElementById('loading-screen');
    const mainContent = document.getElementById('main-content');
    const holdingsBtn = document.getElementById('holdings-btn');
    const holdingsSection = document.getElementById('holdings-section');

    // Simulate loading time and transition to main content
    setTimeout(() => {
        loadingScreen.style.opacity = '0';
        setTimeout(() => {
            loadingScreen.style.display = 'none';
            mainContent.classList.remove('hidden');
        }, 500);
    }, 3000); // 3 second loading screen

    // Navigation button functionality
    const homeBtn = document.getElementById('home-btn');
    const aboutUsBtn = document.getElementById('about-us-btn');
    const aboutSolmanBtn = document.getElementById('about-solman-btn');
    const teamBtn = document.getElementById('team-btn');
    const performanceBtn = document.getElementById('performance-btn');
    
    // Navigation functions
    homeBtn?.addEventListener('click', function() {
        document.getElementById('home').scrollIntoView({ 
            behavior: 'smooth',
            block: 'start'
        });
    });
    
    aboutUsBtn?.addEventListener('click', function() {
        document.getElementById('about').scrollIntoView({ 
            behavior: 'smooth',
            block: 'start'
        });
    });
    
    aboutSolmanBtn?.addEventListener('click', function() {
        document.getElementById('solman-story').scrollIntoView({ 
            behavior: 'smooth',
            block: 'start'
        });
    });
    
        holdingsBtn?.addEventListener('click', function() {
        holdingsSection.scrollIntoView({ 
            behavior: 'smooth',
            block: 'start'
        });
    });
      
    teamBtn?.addEventListener('click', function() {
        document.getElementById('team').scrollIntoView({ 
            behavior: 'smooth',
            block: 'start'
        });
    });
    
    performanceBtn?.addEventListener('click', function() {
        document.getElementById('performance').scrollIntoView({ 
            behavior: 'smooth',
            block: 'start'
        });
    });

    // Initialize performance chart
    initializeChart();
    
    // Contract Address below video copy functionality
    const belowCopyBtn = document.getElementById('below-copy-btn');
    const belowContractAddress = document.getElementById('below-contract-address');
    
    belowCopyBtn?.addEventListener('click', function() {
        const address = belowContractAddress.textContent;
        navigator.clipboard.writeText(address).then(() => {
            const originalIcon = belowCopyBtn.innerHTML;
            belowCopyBtn.innerHTML = `
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M20 6L9 17l-5-5"/>
                </svg>
            `;
            belowCopyBtn.style.background = 'var(--gradient-twilight)';
            
            setTimeout(() => {
                belowCopyBtn.innerHTML = originalIcon;
                belowCopyBtn.style.background = 'var(--gradient-primary)';
            }, 1500);
        }).catch(() => {
            // Fallback for older browsers
            const textArea = document.createElement('textarea');
            textArea.value = address;
            document.body.appendChild(textArea);
            textArea.select();
            document.execCommand('copy');
            document.body.removeChild(textArea);
            
            belowCopyBtn.innerHTML = `
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M20 6L9 17l-5-5"/>
                </svg>
            `;
            setTimeout(() => {
                belowCopyBtn.innerHTML = `
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <rect x="9" y="9" width="13" height="13" rx="2" ry="2"/>
                        <path d="M5 15H4a2 2 0 01-2-2V4a2 2 0 012-2h9a2 2 0 012 2v1"/>
                    </svg>
                `;
            }, 1500);
        });
    });
});

// Performance Chart Creation
function initializeChart() {
    const ctx = document.getElementById('performance-chart').getContext('2d');
    
    // Sample data based on your chart screenshot - showing growth over time
    const chartData = {
        labels: ['Sep 22', 'Oct 22', 'Nov 22', 'Dec 22', 'Jan 23', 'Feb 23', 'Mar 23', 'Apr 23', 'May 23', 'Jun 23', 'Jul 23', 'Aug 23', 'Sep 23', 'Oct 23', 'Nov 23', 'Dec 23', 'Jan 24', 'Feb 24', 'Mar 24', 'Apr 24', 'May 24', 'Jun 24', 'Jul 24', 'Aug 24', 'Sep 24', 'Oct 24', 'Nov 24', 'Dec 24'],
        datasets: [
            {
                label: 'NegroStrategy Fund',
                data: [8.50, 9.20, 10.15, 11.30, 12.80, 14.25, 15.90, 16.75, 17.20, 18.45, 19.80, 21.30, 23.15, 25.40, 27.85, 32.10, 35.25, 38.90, 42.15, 45.80, 48.20, 52.35, 55.90, 58.45, 61.20, 63.85, 66.30, 68.75],
                borderColor: '#00ff88',
                backgroundColor: 'rgba(0, 255, 136, 0.1)',
                borderWidth: 3,
                fill: true,
                tension: 0.4,
                pointRadius: 0,
                pointHoverRadius: 6,
                pointHoverBackgroundColor: '#00ff88',
                pointHoverBorderColor: '#fff',
                pointHoverBorderWidth: 2
            },
            {
                label: 'Elite Trapper Solman',
                data: [12.50, 13.10, 14.25, 15.80, 17.45, 19.20, 21.35, 22.90, 24.15, 26.80, 29.45, 32.10, 35.75, 39.40, 43.85, 48.20, 52.90, 56.15, 59.80, 62.45, 65.20, 68.90, 71.35, 74.20, 76.85, 79.40, 81.95, 84.50],
                borderColor: '#00ccff',
                backgroundColor: 'rgba(0, 204, 255, 0.1)',
                borderWidth: 3,
                fill: true,
                tension: 0.4,
                pointRadius: 0,
                pointHoverRadius: 6,
                pointHoverBackgroundColor: '#00ccff',
                pointHoverBorderColor: '#fff',
                pointHoverBorderWidth: 2
            }
        ]
    };

    const chartOptions = {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
            legend: {
                display: false // We're using custom legend
            },
            tooltip: {
                mode: 'index',
                intersect: false,
                backgroundColor: 'rgba(0, 0, 0, 0.9)',
                titleColor: '#fff',
                bodyColor: '#fff',
                borderColor: '#00ff88',
                borderWidth: 1,
                cornerRadius: 8,
                displayColors: true,
                callbacks: {
                    label: function(context) {
                        return context.dataset.label + ': $' + context.parsed.y.toFixed(2);
                    }
                }
            }
        },
        scales: {
            x: {
                grid: {
                    color: 'rgba(255, 255, 255, 0.1)',
                    borderColor: 'rgba(255, 255, 255, 0.2)'
                },
                ticks: {
                    color: '#888',
                    font: {
                        weight: 600
                    },
                    maxTicksLimit: 8
                }
            },
            y: {
                grid: {
                    color: 'rgba(255, 255, 255, 0.1)',
                    borderColor: 'rgba(255, 255, 255, 0.2)'
                },
                ticks: {
                    color: '#888',
                    font: {
                        weight: 600
                    },
                    callback: function(value) {
                        return '$' + value.toFixed(2);
                    }
                },
                beginAtZero: false
            }
        },
        interaction: {
            mode: 'nearest',
            axis: 'x',
            intersect: false
        },
        animation: {
            duration: 2000,
            easing: 'easeInOutQuart'
        }
    };

    // Create the chart
    new Chart(ctx, {
        type: 'line',
        data: chartData,
        options: chartOptions
    });
}

// Add smooth scrolling for anchor links
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();
        const target = document.querySelector(this.getAttribute('href'));
        if (target) {
            target.scrollIntoView({
                behavior: 'smooth',
                block: 'start'
            });
        }
    });
});

// Add video autoplay with user interaction fallback
document.addEventListener('DOMContentLoaded', function() {
    const video = document.getElementById('main-video');
    
    // Try to autoplay, fallback to user interaction
    video.addEventListener('loadedmetadata', function() {
        const playPromise = video.play();
        
        if (playPromise !== undefined) {
            playPromise.catch(error => {
                console.log('Autoplay prevented, waiting for user interaction');
                // Add a play button overlay if autoplay fails
                addPlayButton();
            });
        }
    });

    function addPlayButton() {
        const playButton = document.createElement('div');
        playButton.innerHTML = '▶️ Click to Play';
        playButton.style.cssText = `
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background: rgba(0, 255, 136, 0.9);
            color: black;
            padding: 15px 30px;
            border-radius: 8px;
            font-weight: bold;
            cursor: pointer;
            z-index: 10;
            font-size: 1.2rem;
        `;
        
        const videoContainer = document.querySelector('.video-container');
        videoContainer.style.position = 'relative';
        videoContainer.appendChild(playButton);
        
        playButton.addEventListener('click', function() {
            video.play();
            playButton.remove();
        });
    }
});

// Add some interactive effects
document.addEventListener('DOMContentLoaded', function() {
    // Add floating animation to holding cards
    const holdingCards = document.querySelectorAll('.holding-card');
    
    holdingCards.forEach((card, index) => {
        card.addEventListener('mouseenter', function() {
            this.style.transform = 'translateY(-10px) rotateY(5deg)';
        });
        
        card.addEventListener('mouseleave', function() {
            this.style.transform = 'translateY(0) rotateY(0)';
        });
    });

    // Parallax effect removed to fix section overlap issues
}); 