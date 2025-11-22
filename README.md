# 🌡️ PPGCardioApp – Smartphone-Based Cardiac Screening App

PPGCardioApp is a mobile application that performs **cardiac screening** using only the **smartphone camera + flashlight**.  
It uses **Photoplethysmography (PPG)** to detect heart rate, HRV, signal quality, and possible cardiac irregularities.

This project was built for the **INVICTUS 2025 Hackathon – HealthTech & Accessibility** domain.

---

## 🚀 Features

### ✅ **1. Camera-based PPG measurement**
- Uses phone camera + flash for fingertip PPG signal  
- Extracts brightness waveform  
- Computes heart rate (HR) in real time  

### ✅ **2. 3-Level Finger Detection System**
- Brightness threshold check  
- Red/Green color ratio validation  
- Pulsatility waveform check  
Prevents false readings when no finger is placed.

### ✅ **3. Signal Quality Index (SQI)**
- Shows signal reliability to avoid inaccurate HR values  
- Filters noisy, low-quality frames  

### ✅ **4. HRV & Risk Indicators**
- IBI calculation  
- HRV metrics  
- Flag abnormal rhythms (tachycardia, irregular patterns)

### ✅ **5. Offline ML Model Integration**
- Lightweight TFLite model for risk scoring  
- On-device inference  
- No internet required  

### ✅ **6. Offline Report Generation**
Generates a 30-second screening report containing:
- HR  
- HRV  
- SQI  
- ML risk prediction  
- Timestamp  

---

## 🧠 Tech Stack

| Component | Technology |
|----------|------------|
| Language | Kotlin |
| Camera Framework | Android CameraX |
| Signal Processing | Custom PPG pipeline + filters |
| Machine Learning | TensorFlow Lite |
| Visualization | Custom WaveformView |
| Storage | Local JSON/Room (optional) |

---


---

## 🏗️ Project Structure

