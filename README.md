<div align="center">

# Desarrollo de Aplicaciones Multiplataforma (DAM)

## Proyecto Intermodular (GS)

### Tarea 3: Implementación y Evaluación de Proyectos de Programación

---

<p align="center">
  <b>Autor:</b> Isidoro Jiménez García <br>
  <b>Centro:</b> IES Aguadulce <br>
  <b>Curso:</b> 2025-2026
</p>

</div>

---

## Descripción del Proyecto
Repositorio correspondiente a la entrega de la **Tarea 3**.

* **Lenguaje:** Java
* **IDE:** Android Studio
* **Base de datos:** Firebase (Firestore & Auth)
* **Documentación:** Doxygen

### Historial de Cambios
* **2025-12-24:** Sincronización de colección 'cromos' con 'cromosPosesion' para ver las cartas en posesión.
* **2025-12-16:** En la pestaña 'Intercambio' se muestran las cartas que no posees y que otros usuarios tienen repetidas.
* **2025-12-15:** Posibilidad de filtrar por colección.

### Capturas de Pantalla

#### Nueva opción: 'Pedir Cromo'
<div align="left">
  <img width="300" alt="Pedir Cromo" src="https://github.com/user-attachments/assets/878b53d0-13b9-484e-832a-68517667b76c" />
</div>

<br>

#### Gestión de Transacciones
En la pestaña 'Transacciones' se podrán gestionar tanto las peticiones enviadas como las recibidas.

Usuario que solicita (Enviadas) - Usuario que recibe (Peticiones)

<img width="300" alt="Usuario Solicita" src="https://github.com/user-attachments/assets/711e2dd5-f0b5-48f5-a1af-8ac7b7611720" /> | <img width="300" alt="Usuario Recibe" src="https://github.com/user-attachments/assets/61f367a5-3519-49d7-8292-3d15792ede7a" /> 

### Diagrama de Estados
Flujo visual de los estados del cromo/intercambio. Cuando se completa la transación el cromo se añade a la colección:

<div align="left">
  <img width="558" alt="Diagrama de Estados" src="https://github.com/user-attachments/assets/b68e4e5f-80f3-441d-9cdd-7f7aecc6311e" />
</div>
