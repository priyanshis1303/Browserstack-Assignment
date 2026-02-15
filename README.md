# BrowserStack Assignment

## 📌 Overview

This project demonstrates end-to-end test automation and web scraping using **Selenium with Java**, along with **cross-browser parallel execution on BrowserStack**.

The solution extracts real articles from the **El País Opinion** section, processes the data, translates content, and validates execution across multiple browsers and devices.

---

## 🚀 Features Implemented

### 1. Web Scraping with Selenium

* Navigates to **El País → Opinion** section
* Extracts the **first 5 articles**
* Prints:

  * Spanish title
  * Article content

### 2. Image Handling

* Detects and downloads **cover images** of each article
* Saves images locally in the project directory

### 3. Translation & Text Processing

* Translates **Spanish titles → English** using Translation API
* Combines all translated titles
* Finds **repeated words occurring more than twice**
* Prints word frequency analysis

### 4. Cross-Browser Testing on BrowserStack

* Executes tests on **BrowserStack Automate Cloud**
* Runs **5 parallel sessions**
* Covers:

  * Chrome (Windows)
  * Edge (Windows)
  * Safari (macOS)
  * iPhone device
  * Android device
* All sessions **successfully passed**

---

## 🛠 Tech Stack

* **Java**
* **Selenium WebDriver**
* **Maven**
* **BrowserStack Automate**
* **REST Translation API**

---

## ▶️ How to Run Locally

```bash
mvn clean test
```

Ensure:

* Java 21 installed
* Maven configured
* Internet connection available

---

## ☁️ BrowserStack Execution Proof

**Public Build Link:**
👉 *(https://automate.browserstack.com/projects/Default+Project/builds/Untitled+Build+Run/2?tab=tests&testListView=spec&public_token=b32cddb49cde8299f5477dfba6b0680dfd1cc7a5d05f55137dad00a8ef4324e3)*

---

## 📂 Project Structure

```
src/main/java
 └── automation.assignment
     ├── ElPaisScraper.java
     └── BrowserStackParallelTest.java
pom.xml
README.md
```

---
## 🎥 Automation Demo Video
Watch here: https://drive.google.com/file/d/1hm8M9WzYUdgDjET9SM4RUb4-NLFF5Bnp/view?usp=sharing


## 🎯 Assignment Goals Covered

* Real-world **web scraping**
* **API integration** for translation
* **File handling** for image download
* **Text analytics** on extracted data
* **Cloud cross-browser automation** with parallel execution

This demonstrates strong skills in:

**Automation • Selenium • Java • API handling • Cloud testing**

---

## 👩‍💻 Author

**Priyanshi Singh**

---


