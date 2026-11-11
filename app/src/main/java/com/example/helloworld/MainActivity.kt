<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Print Master - Android App Simulator</title>
    <!-- Tailwind CSS CDN -->
    <script src="https://cdn.tailwindcss.com"></script>
    <!-- FontAwesome Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <!-- Google Fonts Inter & Roboto -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <script>
        tailwind.config = {
            theme: {
                extend: {
                    colors: {
                        brand: {
                            50: '#eef2ff',
                            100: '#e0e7ff',
                            500: '#6366f1',
                            600: '#4f46e5',
                            700: '#4338ca',
                            900: '#312e81',
                        },
                        android: {
                            bg: '#f8f9fa',
                            card: '#ffffff',
                            nav: '#1f1f23',
                            accent: '#10b981'
                        }
                    },
                    fontFamily: {
                        sans: ['Roboto', 'Inter', 'sans-serif'],
                    }
                }
            }
        }
    </script>
    <style>
        /* Custom scrollbar for simulator content */
        .no-scrollbar::-webkit-scrollbar {
            display: none;
        }
        .no-scrollbar {
            -ms-overflow-style: none;
            scrollbar-width: none;
        }
        
        .android-frame {
            box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.4), 0 0 0 12px #1e1e24, 0 0 0 14px #33333e;
            border-radius: 44px;
        }

        /* Camera Scanner Frame */
        .card-guide {
            box-shadow: 0 0 0 9999px rgba(0, 0, 0, 0.65);
        }

        @keyframes pulse-ring {
            0% { transform: scale(0.95); opacity: 0.8; }
            50% { transform: scale(1.05); opacity: 0.4; }
            100% { transform: scale(0.95); opacity: 0.8; }
        }

        .pulse-animation {
            animation: pulse-ring 2s infinite ease-in-out;
        }

        @keyframes print-feed {
            0% { transform: translateY(-100%); opacity: 0; }
            30% { opacity: 1; }
            100% { transform: translateY(0); opacity: 1; }
        }

        .animate-print {
            animation: print-feed 3s linear forwards;
        }
    </style>
</head>
<body class="bg-slate-900 text-slate-100 min-h-screen flex flex-col items-center justify-start p-2 sm:p-6 font-sans">

    <!-- Top Web Controls Header -->
    <header class="w-full max-w-5xl mb-4 flex flex-col sm:flex-row items-center justify-between gap-4 border-b border-slate-800 pb-4">
        <div class="flex items-center gap-3">
            <div class="w-10 h-10 bg-indigo-600 rounded-xl flex items-center justify-center text-white text-xl font-bold shadow-lg shadow-indigo-500/30">
                <i class="fa-solid fa-print"></i>
            </div>
            <div>
                <h1 class="text-xl font-bold text-white tracking-wide">Print Master <span class="text-xs px-2 py-0.5 bg-indigo-500/20 text-indigo-300 border border-indigo-500/30 rounded-full">v1.0-APK</span></h1>
                <p class="text-xs text-slate-400">Android 14+ Low Memory (2GB RAM) Optimized Print Utility</p>
            </div>
        </div>

        <!-- Mode Toggle & Build APK Button -->
        <div class="flex items-center gap-3">
            <button onclick="toggleSimView('app')" id="tabBtnApp" class="px-4 py-2 text-xs font-semibold rounded-lg bg-indigo-600 text-white shadow-md transition-all">
                <i class="fa-solid fa-mobile-screen-button mr-1.5"></i> App Simulator
            </button>
            <button onclick="toggleSimView('apk')" id="tabBtnApk" class="px-4 py-2 text-xs font-semibold rounded-lg bg-slate-800 hover:bg-slate-700 text-slate-300 border border-slate-700 transition-all">
                <i class="fa-solid fa-box-archive mr-1.5"></i> Build & Export APK
            </button>
        </div>
    </header>

    <!-- Main Container: Contains Device Simulator & Build Info -->
    <main class="w-full max-w-5xl flex flex-col lg:flex-row items-center justify-center gap-8 py-2">
        
        <!-- LEFT: ANDROID DEVICE SIMULATOR FRAME -->
        <div id="simulatorContainer" class="relative w-full max-w-[390px] h-[780px] bg-slate-950 android-frame overflow-hidden flex flex-col select-none transition-all duration-300 border-4 border-slate-800">
            
            <!-- Android Camera Punch-hole & Speaker Notch -->
            <div class="absolute top-0 left-0 right-0 h-7 bg-transparent z-50 flex justify-between items-center px-6 pt-1 text-slate-400 text-xs font-semibold pointer-events-none">
                <span id="androidClock">09:41</span>
                <div class="w-4 h-4 rounded-full bg-slate-900 border-2 border-slate-800 flex items-center justify-center">
                    <div class="w-1.5 h-1.5 rounded-full bg-slate-950"></div>
                </div>
                <div class="flex items-center gap-1.5 text-[10px]">
                    <i class="fa-solid fa-wifi" id="wifiStatusIcon"></i>
                    <i class="fa-solid fa-battery-three-quarters"></i>
                </div>
            </div>

            <!-- SIMULATOR SCREEN CONTENT (SCROLLABLE APP BODY) -->
            <div id="appBody" class="w-full h-full bg-slate-100 text-slate-900 pt-7 pb-8 flex flex-col overflow-y-auto no-scrollbar relative">
                
                <!-- App Header Bar -->
                <div class="bg-indigo-700 text-white px-4 py-3 shadow-md flex items-center justify-between sticky top-0 z-20">
                    <div class="flex items-center gap-2">
                        <i class="fa-solid fa-print text-lg"></i>
                        <span class="font-bold text-base tracking-wide">Print Master</span>
                    </div>
                    <button onclick="openPrinterModal()" class="flex items-center gap-1.5 text-xs bg-indigo-800/80 hover:bg-indigo-900 px-2.5 py-1 rounded-full border border-indigo-400/30 transition-all">
                        <span class="w-2 h-2 rounded-full bg-emerald-400 animate-ping"></span>
                        <span id="connectedPrinterName" class="truncate max-w-[120px]">HP LaserJet 1008</span>
                        <i class="fa-solid fa-chevron-down text-[10px]"></i>
                    </button>
                </div>

                <!-- Printer Connection Status Banner -->
                <div class="bg-emerald-50 border-b border-emerald-200 px-4 py-2 flex items-center justify-between text-xs text-emerald-800">
                    <div class="flex items-center gap-2">
                        <i class="fa-solid fa-circle-check text-emerald-600"></i>
                        <span>Printer Ready (Wi-Fi Direct)</span>
                    </div>
                    <span class="font-mono text-[10px] text-emerald-600">192.168.1.45</span>
                </div>

                <!-- Main Action Content Area -->
                <div class="p-4 space-y-4 flex-1">
                    
                    <!-- SPECIAL AADHAAR CARD PRINT SECTION -->
                    <div class="bg-gradient-to-br from-amber-50 to-orange-50 border border-amber-200 rounded-2xl p-3.5 shadow-sm relative overflow-hidden">
                        <div class="absolute -right-3 -bottom-3 text-amber-200/50 text-6xl pointer-events-none">
                            <i class="fa-solid fa-id-card"></i>
                        </div>
                        <div class="flex items-start justify-between mb-2">
                            <div>
                                <span class="bg-amber-600 text-white text-[9px] font-bold px-2 py-0.5 rounded-full uppercase tracking-wider">Quick Action</span>
                                <h3 class="text-sm font-bold text-slate-800 mt-1 flex items-center gap-1.5">
                                    <i class="fa-solid fa-address-card text-amber-600"></i> Print Aadhaar Card
                                </h3>
                                <p class="text-[11px] text-slate-600">Scan Front & Back → Auto-stitch on 1 A4 Page</p>
                            </div>
                        </div>

                        <button onclick="startAadhaarScanner()" class="w-full mt-2 bg-amber-600 hover:bg-amber-700 active:scale-95 text-white font-semibold py-2.5 px-4 rounded-xl shadow-md flex items-center justify-center gap-2 text-xs transition-all">
                            <i class="fa-solid fa-camera"></i> Scan both sides & Print
                        </button>
                    </div>

                    <!-- STANDARD DOCUMENT SELECTION SECTION -->
                    <div class="bg-white rounded-2xl p-4 shadow-sm border border-slate-200/80 space-y-3">
                        <h4 class="text-xs font-bold text-slate-500 uppercase tracking-wider flex items-center justify-between">
                            <span>Document Selection</span>
                            <span class="text-[10px] text-indigo-600 font-normal">PDF, Word, Images</span>
                        </h4>

                        <!-- Selected File Preview Box -->
                        <div id="filePreviewBox" class="border-2 border-dashed border-slate-200 rounded-xl p-3 bg-slate-50 flex items-center justify-between transition-all">
                            <div class="flex items-center gap-3">
                                <div id="fileIconContainer" class="w-10 h-10 rounded-lg bg-red-100 text-red-600 flex items-center justify-center text-xl font-bold">
                                    <i class="fa-solid fa-file-pdf"></i>
                                </div>
                                <div class="overflow-hidden">
                                    <p id="fileNameDisplay" class="text-xs font-bold text-slate-800 truncate max-w-[170px]">Aadhaar_Summary_Doc.pdf</p>
                                    <p id="fileMetaDisplay" class="text-[10px] text-slate-500">4 Pages • 1.2 MB • Ready</p>
                                </div>
                            </div>
                            <button onclick="triggerFilePicker()" class="text-xs bg-indigo-50 hover:bg-indigo-100 text-indigo-700 font-semibold px-2.5 py-1.5 rounded-lg border border-indigo-200 transition-all">
                                Change
                            </button>
                        </div>
                        <input type="file" id="hiddenFileInput" class="hidden" accept=".pdf,.doc,.docx,.jpg,.jpeg,.png" onchange="handleFileSelected(event)">

                        <!-- Page Range & Selection Dropdown Section -->
                        <div class="pt-2 border-t border-slate-100 space-y-3">
                            <label class="block text-xs font-semibold text-slate-700">Pages to Print</label>
                            
                            <!-- Page Selection Dropdown -->
                            <div class="grid grid-cols-1 gap-2">
                                <select id="pageRangeMode" onchange="handlePageRangeChange()" class="w-full bg-slate-50 border border-slate-300 text-slate-800 text-xs rounded-xl p-2.5 font-medium focus:ring-2 focus:ring-indigo-500 outline-none">
                                    <option value="all">All Pages (1 to <span class="totalPagesCount">4</span>)</option>
                                    <option value="current">Current Page (Page 1)</option>
                                    <option value="single">Select Single Page from Dropdown...</option>
                                    <option value="custom">Custom Range (e.g. 1, 3, 5-8)</option>
                                </select>

                                <!-- Dropdown for 1-20 Pages (Dynamic) -->
                                <div id="singlePageDropdownContainer" class="hidden">
                                    <label class="text-[10px] text-slate-500 block mb-1">Select Page Number:</label>
                                    <select id="singlePageSelect" class="w-full bg-indigo-50/50 border border-indigo-200 text-indigo-950 text-xs rounded-xl p-2.5 font-semibold focus:ring-2 focus:ring-indigo-500 outline-none">
                                        <!-- Populated via JS -->
                                    </select>
                                </div>

                                <!-- Custom Input Box -->
                                <div id="customPageInputContainer" class="hidden">
                                    <input type="text" id="customPageInput" placeholder="Enter pages e.g. 1-2, 4" class="w-full bg-slate-50 border border-slate-300 text-slate-800 text-xs rounded-xl p-2.5 focus:ring-2 focus:ring-indigo-500 outline-none">
                                </div>
                            </div>
                        </div>

                        <!-- DEFAULT LOCKED PRINT SETTINGS -->
                        <div class="pt-2 border-t border-slate-100 space-y-2">
                            <h5 class="text-[11px] font-semibold text-slate-500 uppercase tracking-wide">Print Defaults</h5>
                            
                            <div class="grid grid-cols-2 gap-2 text-xs">
                                <!-- Paper Size (Locked A4) -->
                                <div class="bg-slate-100 p-2 rounded-xl flex items-center justify-between border border-slate-200">
                                    <span class="text-slate-500 text-[10px]">Paper Size</span>
                                    <span class="font-bold text-slate-800 flex items-center gap-1">
                                        <i class="fa-solid fa-lock text-[9px] text-slate-400"></i> A4
                                    </span>
                                </div>

                                <!-- Color Mode (Locked B&W) -->
                                <div class="bg-slate-100 p-2 rounded-xl flex items-center justify-between border border-slate-200">
                                    <span class="text-slate-500 text-[10px]">Color</span>
                                    <span class="font-bold text-slate-800 flex items-center gap-1">
                                        <i class="fa-solid fa-lock text-[9px] text-slate-400"></i> B & W
                                    </span>
                                </div>
                            </div>

                            <!-- Copies Selector -->
                            <div class="flex items-center justify-between bg-slate-50 p-2.5 rounded-xl border border-slate-200">
                                <span class="text-xs font-semibold text-slate-700">Number of Copies</span>
                                <div class="flex items-center gap-3">
                                    <button onclick="updateCopies(-1)" class="w-7 h-7 rounded-lg bg-white border border-slate-300 text-slate-700 font-bold flex items-center justify-center hover:bg-slate-100 active:scale-95 shadow-sm text-sm">-</button>
                                    <span id="copiesVal" class="text-sm font-bold text-slate-800 w-4 text-center">1</span>
                                    <button onclick="updateCopies(1)" class="w-7 h-7 rounded-lg bg-white border border-slate-300 text-slate-700 font-bold flex items-center justify-center hover:bg-slate-100 active:scale-95 shadow-sm text-sm">+</button>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>

                <!-- BOTTOM FIXED PRINT BUTTON -->
                <div class="p-4 bg-white border-t border-slate-200 sticky bottom-0 z-20 shadow-lg">
                    <button onclick="executePrintJob()" class="w-full bg-indigo-600 hover:bg-indigo-700 active:scale-98 text-white font-bold py-3.5 px-4 rounded-2xl shadow-lg shadow-indigo-500/30 flex items-center justify-center gap-2 text-sm tracking-wide transition-all">
                        <i class="fa-solid fa-print text-lg"></i>
                        <span>PRINT NOW</span>
                    </button>
                </div>

                <!-- MODAL 1: AADHAAR CARD CAMERA SCANNER SIMULATION -->
                <div id="aadhaarScannerModal" class="hidden fixed inset-0 bg-black/90 z-50 flex flex-col justify-between p-4 text-white animate-fadeIn">
                    <!-- Scanner Top Header -->
                    <div class="flex items-center justify-between pt-4 px-2">
                        <button onclick="closeAadhaarScanner()" class="w-9 h-9 rounded-full bg-white/10 flex items-center justify-center text-white text-base">
                            <i class="fa-solid fa-xmark"></i>
                        </button>
                        <div class="text-center">
                            <h4 id="scannerStepTitle" class="text-sm font-bold tracking-wide text-amber-400">STEP 1: SCAN FRONT SIDE</h4>
                            <p class="text-[10px] text-slate-300">Align Aadhaar Front (Photo & Name)</p>
                        </div>
                        <div class="w-9"></div>
                    </div>

                    <!-- Camera Viewport & Overlay Frame -->
                    <div class="relative flex-1 my-6 flex items-center justify-center overflow-hidden rounded-2xl border border-white/20 bg-slate-900">
                        <!-- Simulated Live Camera Background -->
                        <div id="cameraBgSimulation" class="absolute inset-0 bg-cover bg-center transition-all duration-300 opacity-80" style="background-image: url('https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?auto=format&fit=crop&w=600&q=80');"></div>
                        
                        <!-- Card Outline Frame Guide -->
                        <div class="relative w-[280px] h-[175px] border-2 border-dashed border-amber-400 rounded-xl z-10 flex flex-col justify-between p-3 card-guide shadow-2xl">
                            <div class="flex justify-between items-start text-amber-300/80 text-[10px] font-mono">
                                <span>FRONT SIDE</span>
                                <i class="fa-solid fa-address-card"></i>
                            </div>
                            
                            <!-- Mock Content overlay inside camera -->
                            <div id="cardFrontOverlay" class="bg-white/90 rounded p-2 text-slate-900 text-xs shadow-md">
                                <div class="flex items-center gap-2">
                                    <div class="w-8 h-10 bg-slate-300 rounded flex items-center justify-center text-slate-600 text-lg">
                                        <i class="fa-solid fa-user"></i>
                                    </div>
                                    <div>
                                        <div class="h-2 w-20 bg-slate-700 rounded mb-1"></div>
                                        <div class="h-1.5 w-16 bg-slate-400 rounded mb-1"></div>
                                        <div class="h-1.5 w-24 bg-slate-400 rounded"></div>
                                    </div>
                                </div>
                            </div>

                            <div id="cardBackOverlay" class="hidden bg-white/90 rounded p-2 text-slate-900 text-xs shadow-md">
                                <div>
                                    <div class="h-2 w-28 bg-slate-700 rounded mb-1"></div>
                                    <div class="h-1.5 w-36 bg-slate-400 rounded mb-1"></div>
                                    <div class="h-1.5 w-32 bg-slate-400 rounded"></div>
                                </div>
                            </div>

                            <div class="text-center text-amber-300/80 text-[9px] font-mono uppercase tracking-widest">
                                Place Card Within Frame
                            </div>
                        </div>
                    </div>

                    <!-- Bottom Controls -->
                    <div class="pb-6 text-center space-y-3">
                        <button onclick="captureCardStep()" id="captureBtn" class="w-16 h-16 rounded-full border-4 border-white bg-amber-500 active:scale-90 text-white text-xl flex items-center justify-center mx-auto shadow-lg transition-transform">
                            <i class="fa-solid fa-camera"></i>
                        </button>
                        <p class="text-[11px] text-slate-400">Tap shutter to capture front side</p>
                    </div>
                </div>

                <!-- MODAL 2: AADHAAR STITCHED PREVIEW & PRINT -->
                <div id="aadhaarStitchPreviewModal" class="hidden fixed inset-0 bg-slate-900/95 z-50 flex flex-col p-4 text-white overflow-y-auto">
                    <div class="flex items-center justify-between mb-3 border-b border-slate-700 pb-2">
                        <h4 class="text-sm font-bold text-amber-400 flex items-center gap-2">
                            <i class="fa-solid fa-file-lines"></i> Aadhaar Single Page Preview (A4 B&W)
                        </h4>
                        <button onclick="closeStitchPreview()" class="text-slate-400 hover:text-white text-lg">
                            <i class="fa-solid fa-xmark"></i>
                        </button>
                    </div>

                    <!-- Simulated A4 Paper Canvas with Both Sides -->
                    <div class="flex-1 flex justify-center py-2">
                        <div class="w-[260px] h-[368px] bg-white text-slate-900 rounded shadow-2xl p-4 flex flex-col items-center justify-start space-y-6 border border-slate-300 relative">
                            <span class="text-[8px] font-mono text-slate-400 absolute top-1 right-2">A4 Paper (210 x 297 mm) - Monochrome</span>
                            
                            <!-- Stitched Front Side Card (B&W Filter applied) -->
                            <div class="w-full h-[120px] border border-slate-800 rounded p-2.5 bg-slate-50 flex flex-col justify-between grayscale contrast-200 shadow-sm">
                                <div class="flex justify-between items-center border-b border-slate-400 pb-1">
                                    <span class="text-[9px] font-bold tracking-tight">GOVERNMENT OF INDIA</span>
                                    <span class="text-[8px] font-bold">AADHAAR</span>
                                </div>
                                <div class="flex items-center gap-3 my-1">
                                    <div class="w-10 h-12 bg-slate-800 rounded flex items-center justify-center text-white text-lg">
                                        <i class="fa-solid fa-user"></i>
                                    </div>
                                    <div class="space-y-1">
                                        <p class="text-[10px] font-bold">RAHUL KUMAR SHARMA</p>
                                        <p class="text-[8px]">DOB: 15/08/1992 | Male</p>
                                        <p class="text-[9px] font-mono font-bold tracking-wider">XXXX XXXX 4821</p>
                                    </div>
                                </div>
                                <div class="text-[7px] text-center font-semibold border-t border-slate-300 pt-0.5">FRONT SIDE</div>
                            </div>

                            <!-- Stitched Back Side Card (B&W Filter applied) -->
                            <div class="w-full h-[120px] border border-slate-800 rounded p-2.5 bg-slate-50 flex flex-col justify-between grayscale contrast-200 shadow-sm">
                                <div class="flex justify-between items-center border-b border-slate-400 pb-1">
                                    <span class="text-[8px] font-bold">ADDRESS DETAILS</span>
                                    <i class="fa-solid fa-qrcode text-base"></i>
                                </div>
                                <div class="my-1 space-y-0.5">
                                    <p class="text-[8px] font-semibold">S/O: Suresh Sharma, H-45, Sector 12</p>
                                    <p class="text-[8px]">New Delhi, PIN: 110001, India</p>
                                    <p class="text-[9px] font-mono font-bold tracking-wider mt-1">XXXX XXXX 4821</p>
                                </div>
                                <div class="text-[7px] text-center font-semibold border-t border-slate-300 pt-0.5">BACK SIDE</div>
                            </div>
                        </div>
                    </div>

                    <!-- Confirm Print Button -->
                    <div class="pt-3">
                        <button onclick="printStitchedAadhaar()" class="w-full bg-amber-600 hover:bg-amber-700 font-bold py-3 px-4 rounded-xl shadow-lg flex items-center justify-center gap-2 text-xs">
                            <i class="fa-solid fa-print"></i> Send Aadhaar to Printer Now
                        </button>
                    </div>
                </div>

                <!-- MODAL 3: WI-FI PRINTER SELECTION -->
                <div id="printerModal" class="hidden fixed inset-0 bg-black/70 z-50 flex items-end sm:items-center justify-center p-0 sm:p-4 animate-fadeIn">
                    <div class="w-full bg-white rounded-t-3xl sm:rounded-2xl p-4 text-slate-800 space-y-4 max-h-[80vh] overflow-y-auto">
                        <div class="flex items-center justify-between border-b pb-2">
                            <h3 class="font-bold text-sm text-slate-800 flex items-center gap-2">
                                <i class="fa-solid fa-wifi text-indigo-600"></i> Select Wi-Fi Printer
                            </h3>
                            <button onclick="closePrinterModal()" class="text-slate-400 hover:text-slate-600">
                                <i class="fa-solid fa-xmark text-lg"></i>
                            </button>
                        </div>

                        <!-- Scan Status -->
                        <div class="flex items-center justify-between bg-indigo-50 p-3 rounded-xl border border-indigo-100 text-xs">
                            <div class="flex items-center gap-2 text-indigo-800">
                                <i class="fa-solid fa-arrows-rotate animate-spin text-indigo-600"></i>
                                <span>Scanning local Wi-Fi network...</span>
                            </div>
                            <span class="text-[10px] bg-indigo-200 text-indigo-900 px-2 py-0.5 rounded-full font-mono">mDNS / IPP</span>
                        </div>

                        <!-- Available Printers List -->
                        <div class="space-y-2">
                            <button onclick="selectPrinter('HP LaserJet 1008', '192.168.1.45')" class="w-full p-3 rounded-xl border border-indigo-500 bg-indigo-50/50 flex items-center justify-between hover:bg-indigo-100/50 transition-all text-left">
                                <div class="flex items-center gap-3">
                                    <div class="w-8 h-8 rounded-lg bg-indigo-600 text-white flex items-center justify-center text-sm">
                                        <i class="fa-solid fa-print"></i>
                                    </div>
                                    <div>
                                        <p class="text-xs font-bold text-slate-800">HP LaserJet 1008 (Default)</p>
                                        <p class="text-[10px] text-slate-500">IP: 192.168.1.45 • Ready</p>
                                    </div>
                                </div>
                                <i class="fa-solid fa-circle-check text-indigo-600 text-base"></i>
                            </button>

                            <button onclick="selectPrinter('Epson EcoTank L3250', '192.168.1.88')" class="w-full p-3 rounded-xl border border-slate-200 bg-white flex items-center justify-between hover:bg-slate-50 transition-all text-left">
                                <div class="flex items-center gap-3">
                                    <div class="w-8 h-8 rounded-lg bg-slate-200 text-slate-700 flex items-center justify-center text-sm">
                                        <i class="fa-solid fa-print"></i>
                                    </div>
                                    <div>
                                        <p class="text-xs font-bold text-slate-800">Epson EcoTank L3250</p>
                                        <p class="text-[10px] text-slate-500">IP: 192.168.1.88 • Idle</p>
                                    </div>
                                </div>
                                <i class="fa-solid fa-chevron-right text-slate-300 text-xs"></i>
                            </button>
                        </div>
                    </div>
                </div>

                <!-- MODAL 4: PRINT SPOOLER ANIMATED MODAL -->
                <div id="printSpoolerModal" class="hidden fixed inset-0 bg-black/80 z-50 flex items-center justify-center p-4">
                    <div class="bg-white rounded-3xl p-6 w-full max-w-[320px] text-center space-y-4 shadow-2xl">
                        <div class="w-16 h-16 bg-indigo-100 rounded-full flex items-center justify-center mx-auto text-indigo-600 text-2xl relative">
                            <i class="fa-solid fa-print pulse-animation"></i>
                        </div>
                        <div>
                            <h4 class="font-bold text-slate-800 text-base">Sending to Printer</h4>
                            <p id="spoolerPrinterName" class="text-xs text-indigo-600 font-medium">HP LaserJet 1008</p>
                        </div>

                        <!-- Animated Paper Feeding Graphic -->
                        <div class="w-full h-24 bg-slate-100 rounded-xl overflow-hidden relative border border-slate-200 flex items-center justify-center">
                            <div class="w-32 h-20 bg-white shadow-md border border-slate-300 rounded p-2 text-[8px] text-left text-slate-400 flex flex-col justify-between animate-print">
                                <div class="flex justify-between">
                                    <span class="font-bold text-slate-700">A4 B&W PRINT</span>
                                    <span>Page 1</span>
                                </div>
                                <div class="space-y-1">
                                    <div class="h-1 bg-slate-300 rounded w-full"></div>
                                    <div class="h-1 bg-slate-200 rounded w-3/4"></div>
                                </div>
                                <div class="text-[6px] text-slate-400 text-right">Print Master OS</div>
                            </div>
                        </div>

                        <!-- Progress Bar -->
                        <div class="w-full bg-slate-200 h-2 rounded-full overflow-hidden">
                            <div id="spoolerProgressBar" class="bg-indigo-600 h-full w-0 transition-all duration-300"></div>
                        </div>

                        <p id="spoolerStatusText" class="text-xs text-slate-500 font-mono">Rendering PDF pages (1/1)...</p>
                    </div>
                </div>

            </div>

            <!-- Android Bottom Navigation Bar -->
            <div class="h-7 bg-slate-950 flex items-center justify-center gap-12 text-slate-400 text-xs z-50">
                <i class="fa-solid fa-chevron-left"></i>
                <i class="fa-regular fa-circle text-[10px]"></i>
                <i class="fa-solid fa-bars text-[10px]"></i>
            </div>
        </div>

        <!-- RIGHT: BUILD APK & ARCHITECTURE PANEL -->
        <div id="apkPanel" class="w-full max-w-md bg-slate-800 border border-slate-700 rounded-3xl p-6 text-slate-200 shadow-2xl flex flex-col space-y-5">
            <div>
                <div class="flex items-center gap-2 text-indigo-400 text-xs font-bold uppercase tracking-wider mb-1">
                    <i class="fa-solid fa-microchip"></i> Android 14 Optimized Architecture
                </div>
                <h2 class="text-xl font-bold text-white">Print Master Package Details</h2>
                <p class="text-xs text-slate-400 mt-1">Designed for low-ram (2GB RAM) devices with zero background memory drag.</p>
            </div>

            <div class="space-y-3">
                <div class="bg-slate-900/80 p-3 rounded-xl border border-slate-700 flex justify-between items-center text-xs">
                    <span class="text-slate-400">Target SDK</span>
                    <span class="font-bold text-emerald-400">Android 14 (API 34+)</span>
                </div>
                <div class="bg-slate-900/80 p-3 rounded-xl border border-slate-700 flex justify-between items-center text-xs">
                    <span class="text-slate-400">Memory Usage</span>
                    <span class="font-bold text-emerald-400">&lt; 42 MB RAM peak</span>
                </div>
                <div class="bg-slate-900/80 p-3 rounded-xl border border-slate-700 flex justify-between items-center text-xs">
                    <span class="text-slate-400">Core Services</span>
                    <span class="font-bold text-indigo-300">Android PrintManager + CameraX</span>
                </div>
            </div>

            <!-- Download / Source Simulation Controls -->
            <div class="pt-4 border-t border-slate-700 space-y-3">
                <h4 class="text-xs font-bold text-slate-300">Download Package Simulation</h4>
                <button onclick="downloadMockApk()" class="w-full bg-emerald-600 hover:bg-emerald-500 text-white font-bold py-3 px-4 rounded-xl shadow-lg flex items-center justify-center gap-2 text-xs transition-all">
                    <i class="fa-solid fa-download"></i> Download Mock APK Build (PrintMaster.apk)
                </button>
                <button onclick="downloadMockSource()" class="w-full bg-slate-700 hover:bg-slate-600 text-slate-200 font-bold py-2.5 px-4 rounded-xl border border-slate-600 flex items-center justify-center gap-2 text-xs transition-all">
                    <i class="fa-solid fa-code"></i> Download Kotlin Source Zip
                </button>
            </div>

            <!-- Activity Logs / System Terminal -->
            <div class="bg-slate-950 p-3 rounded-xl border border-slate-900 text-[11px] font-mono space-y-1 text-slate-400 max-h-36 overflow-y-auto">
                <p class="text-emerald-400">[SYSTEM] PrintMaster App initialized.</p>
                <p>[MDNS] Registered Wi-Fi listener on 192.168.1.X</p>
                <p>[RENDERER] Default paper set to A4 (210x297mm).</p>
                <p class="text-amber-400">[CAMERA] CameraX card auto-crop pipeline ready.</p>
            </div>
        </div>

    </main>

    <!-- JavaScript Logic & Interactivity -->
    <script>
        // Global State
        let currentTotalPages = 4;
        let selectedCopies = 1;
        let selectedPrinter = "HP LaserJet 1008";
        let aadhaarStep = 1; // 1: Front, 2: Back

        // Clock Update
        function updateClock() {
            const now = new Date();
            const hrs = String(now.getHours()).padStart(2, '0');
            const mins = String(now.getMinutes()).padStart(2, '0');
            document.getElementById('androidClock').textContent = `${hrs}:${mins}`;
        }
        setInterval(updateClock, 1000);
        updateClock();

        // Populate Single Page Dropdown (Pages 1 to 20 or dynamic)
        function populatePageDropdown(maxPages) {
            const select = document.getElementById('singlePageSelect');
            select.innerHTML = '';
            const limit = Math.max(maxPages, 20); // allow up to 20 as requested
            for (let i = 1; i <= limit; i++) {
                const opt = document.createElement('option');
                opt.value = i;
                opt.textContent = `Page ${i}${i <= maxPages ? '' : ' (Blank / Extended)'}`;
                select.appendChild(opt);
            }
        }
        populatePageDropdown(4);

        // Handle Page Range Dropdown Changes
        function handlePageRangeChange() {
            const mode = document.getElementById('pageRangeMode').value;
            const singleContainer = document.getElementById('singlePageDropdownContainer');
            const customContainer = document.getElementById('customPageInputContainer');

            singleContainer.classList.add('hidden');
            customContainer.classList.add('hidden');

            if (mode === 'single') {
                singleContainer.classList.remove('hidden');
            } else if (mode === 'custom') {
                customContainer.classList.remove('hidden');
            }
        }

        // Copies Counter
        function updateCopies(delta) {
            selectedCopies = Math.max(1, selectedCopies + delta);
            document.getElementById('copiesVal').textContent = selectedCopies;
        }

        // File Selection Trigger Simulator
        function triggerFilePicker() {
            document.getElementById('hiddenFileInput').click();
        }

        function handleFileSelected(event) {
            const file = event.target.files[0];
            if (!file) return;

            const nameDisplay = document.getElementById('fileNameDisplay');
            const metaDisplay = document.getElementById('fileMetaDisplay');
            const iconContainer = document.getElementById('fileIconContainer');

            nameDisplay.textContent = file.name;

            // Generate mock page count based on name/type
            const ext = file.name.split('.').pop().toLowerCase();
            if (ext === 'pdf') {
                currentTotalPages = 6;
                iconContainer.className = "w-10 h-10 rounded-lg bg-red-100 text-red-600 flex items-center justify-center text-xl font-bold";
                iconContainer.innerHTML = '<i class="fa-solid fa-file-pdf"></i>';
            } else if (ext === 'doc' || ext === 'docx') {
                currentTotalPages = 3;
                iconContainer.className = "w-10 h-10 rounded-lg bg-blue-100 text-blue-600 flex items-center justify-center text-xl font-bold";
                iconContainer.innerHTML = '<i class="fa-solid fa-file-word"></i>';
            } else {
                currentTotalPages = 1;
                iconContainer.className = "w-10 h-10 rounded-lg bg-emerald-100 text-emerald-600 flex items-center justify-center text-xl font-bold";
                iconContainer.innerHTML = '<i class="fa-solid fa-file-image"></i>';
            }

            metaDisplay.textContent = `${currentTotalPages} Pages • ${(file.size / 1024 / 1024).toFixed(1)} MB • Ready`;
            
            // Update UI elements dependent on total pages
            document.querySelectorAll('.totalPagesCount').forEach(el => el.textContent = currentTotalPages);
            populatePageDropdown(currentTotalPages);
        }

        // Aadhaar Workflow Logic
        function startAadhaarScanner() {
            aadhaarStep = 1;
            document.getElementById('scannerStepTitle').textContent = "STEP 1: SCAN FRONT SIDE";
            document.getElementById('cardFrontOverlay').classList.remove('hidden');
            document.getElementById('cardBackOverlay').classList.add('hidden');
            document.getElementById('aadhaarScannerModal').classList.remove('hidden');
        }

        function closeAadhaarScanner() {
            document.getElementById('aadhaarScannerModal').classList.add('hidden');
        }

        function captureCardStep() {
            if (aadhaarStep === 1) {
                // Move to Back side
                aadhaarStep = 2;
                document.getElementById('scannerStepTitle').textContent = "STEP 2: SCAN BACK SIDE";
                document.getElementById('cardFrontOverlay').classList.add('hidden');
                document.getElementById('cardBackOverlay').classList.remove('hidden');
                
                // Switch bg image simulation
                document.getElementById('cameraBgSimulation').style.backgroundImage = "url('https://images.unsplash.com/photo-1557683316-973673baf926?auto=format&fit=crop&w=600&q=80')";
            } else {
                // Finish capturing both sides -> Show Stitched Preview
                closeAadhaarScanner();
                document.getElementById('aadhaarStitchPreviewModal').classList.remove('hidden');
            }
        }

        function closeStitchPreview() {
            document.getElementById('aadhaarStitchPreviewModal').classList.add('hidden');
        }

        function printStitchedAadhaar() {
            closeStitchPreview();
            executePrintJob("Aadhaar Card (Front + Back Stitched)");
        }

        // Printer Picker Modal
        function openPrinterModal() {
            document.getElementById('printerModal').classList.remove('hidden');
        }

        function closePrinterModal() {
            document.getElementById('printerModal').classList.add('hidden');
        }

        function selectPrinter(name, ip) {
            selectedPrinter = name;
            document.getElementById('connectedPrinterName').textContent = name;
            closePrinterModal();
        }

        // Execute Print Job & Spooler Animation
        function executePrintJob(jobNameOverride) {
            const spooler = document.getElementById('printSpoolerModal');
            const progress = document.getElementById('spoolerProgressBar');
            const statusText = document.getElementById('spoolerStatusText');
            document.getElementById('spoolerPrinterName').textContent = selectedPrinter;

            spooler.classList.remove('hidden');
            progress.style.width = '0%';
            statusText.textContent = `Connecting to ${selectedPrinter}...`;

            setTimeout(() => {
                progress.style.width = '40%';
                statusText.textContent = `Formatting A4 Black & White output...`;
            }, 800);

            setTimeout(() => {
                progress.style.width = '80%';
                statusText.textContent = `Streaming data to printer via Wi-Fi...`;
            }, 1800);

            setTimeout(() => {
                progress.style.width = '100%';
                statusText.textContent = `Print completed successfully!`;
            }, 2800);

            setTimeout(() => {
                spooler.classList.add('hidden');
            }, 3500);
        }

        // Toggle View Tab
        function toggleSimView(view) {
            const tabApp = document.getElementById('tabBtnApp');
            const tabApk = document.getElementById('tabBtnApk');

            if (view === 'app') {
                tabApp.className = "px-4 py-2 text-xs font-semibold rounded-lg bg-indigo-600 text-white shadow-md transition-all";
                tabApk.className = "px-4 py-2 text-xs font-semibold rounded-lg bg-slate-800 hover:bg-slate-700 text-slate-300 border border-slate-700 transition-all";
            } else {
                tabApk.className = "px-4 py-2 text-xs font-semibold rounded-lg bg-indigo-600 text-white shadow-md transition-all";
                tabApp.className = "px-4 py-2 text-xs font-semibold rounded-lg bg-slate-800 hover:bg-slate-700 text-slate-300 border border-slate-700 transition-all";
            }
        }

        // Download Mock Files
        function downloadMockApk() {
            const blob = new Blob(["Simulated Print Master Android 14 APK Binary File"], { type: "application/vnd.android.package-archive" });
            const url = URL.createObjectURL(blob);
            const a = document.createElement("a");
            a.href = url;
            a.download = "PrintMaster-v1.0-release.apk";
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
        }

        function downloadMockSource() {
            const blob = new Blob(["Print Master - Android Kotlin Source Code Package"], { type: "application/zip" });
            const url = URL.createObjectURL(blob);
            const a = document.createElement("a");
            a.href = url;
            a.download = "PrintMaster-Kotlin-Source.zip";
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
        }
    </script>
</body>
</html>
