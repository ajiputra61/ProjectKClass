## Projek untuk CALAS

# Data Diri

Nama: Aji Putra Pamungkas<br>
NPM: 50423097<br>
Kelas: 2IA25<br>

# Keterangan Projek

Projek ini terinspirasi dari v class gunadarma <br>
Projek ini merupakan aplikasi desktop menggunakan Java <br>
Projek ini dibuat untuk user melakukan beberapa kuis, dimana kuis-kuis tersebut merupakan file csv <br>
Projek ini menerapkan database sehingga dapat membuat dan menyimpan lebih dari satu user/session <br>
Saya ingin membuat projek ini agar mempunyai leaderboard dan mode admin <br>

# Platform dan Dependansi yang Digunakan

APACHE NETBEANS 25 <br>
JDK 24 WINDOWS X64 (JAVA ANT) <br>
MYSQL SHELL & WORKBENCH 8.0.43 <br>
MYSQL CONNECTOR /J 9.4.0 <br>

# Inisialisasi Database Awal dengan mySql

```
CREATE DATABASE kclassDB;
USE kclassDB;
CREATE TABLE kUser(
	idUser INT NOT NULL AUTO_INCREMENT,
	username VARCHAR(255),
    pass VARCHAR(255),
    PRIMARY KEY (idUser)
);

CREATE TABLE kCSV(
	idCSV INT NOT NULL AUTO_INCREMENT,
	nama VARCHAR(255),
    url VARCHAR(255),
    PRIMARY KEY (idCSV)
);

CREATE TABLE kScore(
	idUser INT,
    idCSV INT,
    score INT,
    FOREIGN KEY (idUser) REFERENCES kUser(idUser) ON DELETE CASCADE,
    FOREIGN KEY (idCSV) REFERENCES kCSV(idCSV) ON DELETE CASCADE
);

INSERT INTO kUser (username, pass, peran) VALUES ("kurukou", "kurukou");

INSERT INTO kCSV (nama, url)
VALUES
("Ibukota - Provinsi", "./src/csv/Provinsi.csv"),
("Ibukota - Negara", "./src/csv/Negara.csv"),
("Unsur Kimia - Simbol", "./src/csv/Unsur Kimia.csv"),
("Hewan - Habitat Asli", "./src/csv/Habitat Hewan.csv"),
("Tanaman - Manfaat", "./src/csv/Manfaat Tanaman.csv"),
("Judul Film - Sutradara", "./src/csv/Sutradara Film.csv"),
("Penulis - Karya Terkenal", "./src/csv/Karya Terkenal Penulis.csv"),
("Gunung di Indonesia - Lokasi", "./src/csv/Lokasi Gunung Indonesia.csv"),
("Bahasa Pemrograman - Tahun Diciptakan", "./src/csv/Bahasa Pemrograman.csv"),
("Penemuan Bersejarah - Tahun", "./src/csv/Penemuan Bersejarah.csv");
```

SELECT _ FROM kUser;
SELECT _ FROM kCSV;
SELECT \* FROM kSCORE;
