# Proyecto_Acomp_Construcción_de_Software
## Diagrama de clases UML
![Class Diagram Constr Proy](https://github.com/user-attachments/assets/4307cd7e-15e7-4c88-8e3e-0f0fa76bdf94)

## Cómo compilar/ejecutar Maven
### 1️⃣ Verifica pom.xml
Asegúrate de que en la raíz del proyecto exista un archivo llamado **`pom.xml`**.  
Este archivo es el que define las dependencias, la configuración y la estructura del proyecto Maven.
### 2️⃣ Compilar el proyecto
```bash
mvn compile
```
### 3️⃣ Ejecutar la clase principal
Ejecuta el proyecto con:
```bash
mvn exec:java -Dexec.mainClass="Main.java"
```
