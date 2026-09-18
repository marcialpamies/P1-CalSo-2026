# Práctica 1: revisión estática de código con SonarQube for Eclipse (SonarLint)

## Índice

- [0. Requisitos previos](#0-requisitos-previos)
- [1. Objetivos](#1-objetivos)
- [2. Repositorio de prácticas del grupo](#2-repositorio-de-prácticas-del-grupo)
- [3. Proyecto de partida](#3-proyecto-de-partida)
- [4. Preparación inicial del repositorio](#4-preparación-inicial-del-repositorio)
- [5. Instalación y configuración de SonarQube for Eclipse](#5-instalación-y-configuración-de-sonarqube-for-eclipse)
- [6. Análisis inicial](#6-análisis-inicial)
- [7. Resolución de las disconformidades y trabajo con Git](#7-resolución-de-las-disconformidades-y-trabajo-con-git)
- [8. Análisis final](#8-análisis-final)
- [9. Documentación en README_P1.md](#9-documentación-en-readme_p1md)
- [10. Proyecto Eclipse final](#10-proyecto-eclipse-final)
- [11. Entrega](#11-entrega)
- [12. Criterios de evaluación](#12-criterios-de-evaluación)
- [13. Errores y dudas frecuentes](#13-errores-y-dudas-frecuentes)
- [Anexo A. Esqueleto de README_P1.md](#anexo-a-esqueleto-de-readme_p1md)

---

## 0. Requisitos previos

Para realizar la práctica se necesita:

- Java 17 (JDK) o posterior.
- Eclipse IDE con soporte para proyectos Java.
- El plugin **SonarQube for Eclipse**, denominado históricamente **SonarLint for Eclipse**.
- Git.
- Acceso al repositorio Git remoto que utilizará el grupo durante el curso.
- El proyecto Java proporcionado con la práctica.

Para esta práctica **no se necesita**:

- Maven.
- SonarQube Server.
- SonarQube Cloud.
- Una cuenta de Sonar.
- Conectar o vincular el proyecto local con un servidor Sonar.

Todo el análisis de código se realizará **en local y en modo standalone**, desde Eclipse.

> **Importante:** durante la práctica no deben modificarse las reglas activas de Sonar, excluirse ficheros del análisis ni configurarse *Connected Mode*. Todos los grupos deben trabajar con la configuración local por defecto del plugin utilizada en el laboratorio.

### 0.1. Identificación de los autores de los commits

Cada miembro del grupo debe realizar sus propios commits con su identidad correctamente configurada en Git.

En un equipo personal puede utilizarse, si procede:

```bash
git config --global user.name "Nombre Apellidos"
git config --global user.email "correo@dominio.com"
```

En un equipo compartido del laboratorio **no debe utilizarse `--global`**. La configuración se realizará únicamente para el repositorio:

```bash
git config user.name "Nombre Apellidos"
git config user.email "correo@dominio.com"
```

La autoría registrada en el historial Git se utilizará para comprobar qué miembro del grupo ha realizado cada corrección.

---

## 1. Objetivos

El objetivo de la práctica es introducir el uso de herramientas automáticas de **análisis estático de código** y aprender a interpretar y resolver las disconformidades detectadas directamente durante el desarrollo.

Además, se utilizará Git como mecanismo de **trazabilidad del trabajo realizado**, de forma que cada corrección relevante quede identificada mediante un commit independiente y pueda relacionarse con la disconformidad que resuelve.

Al finalizar la práctica el alumnado deberá ser capaz de:

1. instalar y utilizar SonarQube for Eclipse en modo local;
2. ejecutar el análisis estático de un proyecto Java completo;
3. localizar las disconformidades detectadas por la herramienta;
4. consultar la regla asociada a cada disconformidad y comprender su causa;
5. proponer y aplicar una modificación adecuada para resolverla;
6. documentar las disconformidades y las soluciones adoptadas;
7. registrar cada corrección mediante un commit trazable en Git; y
8. comprobar mediante un nuevo análisis que las disconformidades han sido resueltas.

---

## 2. Repositorio de prácticas del grupo

Cada grupo dispondrá de **un único repositorio Git para todas las prácticas de la asignatura**.

No se creará un repositorio independiente para cada práctica.

Dentro del repositorio, cada práctica tendrá su propia carpeta:

```text
REPOSITORIO_DEL_GRUPO/
│
├── README.md
│
├── P1/
│   ├── README_P1.md
│   ├── imagenes/
│   │   ├── sonar_inicial.png
│   │   └── sonar_final.png
│   └── proyecto/
│       └── P1_INICIALES/
│           ├── .project
│           ├── .classpath
│           ├── .settings/
│           └── src/
│
├── P2/
│   ├── README_P2.md
│   ├── imagenes/
│   └── proyecto/
│
├── P3/
│   └── ...
│
└── ...
```

La carpeta correspondiente a esta práctica será obligatoriamente:

```text
P1/
```

### 2.1. Contenido mínimo de `P1/`

Al finalizar la práctica, la carpeta `P1/` deberá contener como mínimo:

```text
P1/
├── README_P1.md
├── imagenes/
│   ├── sonar_inicial.png
│   └── sonar_final.png
└── proyecto/
    └── P1_INICIALES/
        └── ...
```

El fichero `README_P1.md` será el **informe de la práctica** y contendrá:

- nombre y apellidos de los dos miembros del grupo;
- captura de pantalla del análisis inicial;
- relación completa de disconformidades observadas;
- solución adoptada para cada disconformidad;
- miembro del grupo responsable de cada corrección;
- identificador del commit correspondiente a cada corrección; y
- captura de pantalla del análisis final.

La carpeta `proyecto/` contendrá el **proyecto Eclipse final**, con todas las modificaciones realizadas para resolver las disconformidades.

### 2.2. Rama de trabajo

Para esta práctica se trabajará directamente sobre la rama principal:

```text
main
```

No es necesario crear ramas individuales para los miembros del grupo.

Los dos participantes deben realizar commits propios sobre `main`. Antes de comenzar una nueva corrección, especialmente cuando se alternen los miembros del grupo, debe actualizarse el repositorio local:

```bash
git pull --rebase origin main
```

Después de completar y comprobar una corrección:

```bash
git push origin main
```

> **Importante:** el historial de `main` forma parte de las evidencias de la práctica. No deben reescribirse, agruparse o eliminarse posteriormente los commits que acreditan las correcciones realizadas por cada miembro.

---

## 3. Proyecto de partida

Se proporciona un proyecto Java estándar de Eclipse llamado inicialmente:

```text
P1_INICIALES
```

El proyecto **no es Maven**. La carpeta `src` contiene el código Java y Eclipse utilizará `bin` como carpeta de salida de la compilación.

### 3.1. Nombre obligatorio del proyecto

Antes de modificar el código y **antes de realizar la captura inicial**, cada grupo debe cambiar el nombre del proyecto al formato:

```text
P1_<INICIALES_DE_LOS_MIEMBROS_DEL_GRUPO>
```

Las iniciales de los dos miembros se concatenarán en el orden acordado por el grupo, sin espacios.

Por ejemplo, si las iniciales utilizadas por los dos miembros son `MP` y `JLR`:

```text
P1_MPJLR
```

El nombre utilizado en:

- la captura inicial;
- la captura final; y
- la carpeta del proyecto incorporada al repositorio

debe ser **exactamente el mismo**.

### 3.2. Ubicación del proyecto dentro del repositorio

El proyecto deberá situarse desde el comienzo de la práctica en:

```text
P1/proyecto/P1_<INICIALES>/
```

De este modo, las modificaciones realizadas sobre el proyecto quedarán registradas directamente en el repositorio del grupo.

### 3.3. Importación en Eclipse

1. Sitúa el proyecto dentro de `P1/proyecto/`.
2. Abre Eclipse y selecciona el *workspace* de trabajo.
3. Utiliza **File > Import... > General > Existing Projects into Workspace**.
4. Selecciona la carpeta que contiene el proyecto.
5. Importa el proyecto.
6. Renómbralo siguiendo el formato obligatorio indicado anteriormente.
7. Comprueba que Eclipse lo reconoce como proyecto Java.
8. Comprueba que no aparece ningún `pom.xml`.

No modifiques todavía los ficheros Java.

---

## 4. Preparación inicial del repositorio

Antes de resolver ninguna disconformidad debe quedar registrada en `main` la situación inicial de la práctica.

### 4.1. Crear la estructura de `P1/`

Debe prepararse la siguiente estructura:

```text
P1/
├── README_P1.md
├── imagenes/
└── proyecto/
    └── P1_<INICIALES>/
```

El `README_P1.md` puede crearse utilizando el esqueleto incluido en el **Anexo A** de este enunciado.

### 4.2. Commit de partida

Antes de modificar el código Java se realizará un commit que incorpore:

- la estructura de la práctica;
- el proyecto original renombrado;
- el `README_P1.md` inicial; y
- posteriormente, si se desea, la captura inicial obtenida en el apartado 6.

Un mensaje adecuado para el commit de partida sería:

```text
P1 - Incorporar proyecto y estructura inicial
```

Este commit **no cuenta como corrección de una disconformidad**.

A partir de él, las correcciones de Sonar deben quedar reflejadas en commits independientes.

---

## 5. Instalación y configuración de SonarQube for Eclipse

### 5.1. Instalación

1. En Eclipse abre **Help > Eclipse Marketplace...**.
2. Busca `SonarQube`.
3. Instala **SonarQube for Eclipse**.
4. Reinicia Eclipse cuando se solicite.

La extensión se ha denominado históricamente **SonarLint for Eclipse**.

Documentación oficial de instalación:

<https://docs.sonarsource.com/sonarqube-for-eclipse/getting-started/installation>

### 5.2. Trabajo exclusivamente local

La práctica se realizará en **standalone mode**.

Por tanto:

- no se debe crear ninguna conexión con SonarQube Server o SonarQube Cloud;
- no se debe realizar ningún *project binding*;
- no se debe activar *Connected Mode*; y
- no se deben importar perfiles de calidad externos.

En modo standalone, las reglas que se ejecutan localmente pueden consultarse en:

**Window > Preferences > SonarQube > Rules Configuration**

Durante esta práctica **no se debe cambiar su configuración**.

Documentación oficial sobre reglas en modo standalone:

<https://docs.sonarsource.com/sonarqube-for-eclipse/using/rules>

---

## 6. Análisis inicial

Una vez importado y renombrado el proyecto, y **sin haber modificado el código fuente**:

1. selecciona el proyecto completo en **Package Explorer** o **Project Explorer**;
2. pulsa con el botón derecho sobre él;
3. selecciona **SonarQube > Analyze**;
4. abre la vista de resultados de SonarQube si no se muestra automáticamente; y
5. comprueba las disconformidades detectadas en los distintos ficheros Java.

Sonar analiza automáticamente los ficheros abiertos, pero para esta práctica debe realizarse expresamente un **análisis del proyecto completo**.

Documentación oficial sobre el análisis de un proyecto:

<https://docs.sonarsource.com/sonarqube-for-eclipse/using/scan-my-project>

### 6.1. Captura inicial

Realiza una captura inicial en la que se vean simultáneamente y de forma legible:

- el nombre completo del proyecto con el formato `P1_<INICIALES>`; y
- la vista de SonarQube con las disconformidades detectadas inicialmente.

La captura debe permitir comprobar que el análisis corresponde al proyecto de la práctica y que todavía no se han aplicado las correcciones.

El fichero se guardará en:

```text
P1/imagenes/sonar_inicial.png
```

y se mostrará también dentro de `README_P1.md`.

### 6.2. Registrar las disconformidades

Antes de comenzar las correcciones, deben registrarse en `README_P1.md` **todas las disconformidades observadas en el análisis inicial**.

Para cada una deberá anotarse, al menos:

- número correlativo;
- regla Sonar;
- fichero;
- línea o localización;
- descripción del problema.

Ejemplo:

| Nº | Regla Sonar | Archivo | Línea | Disconformidad |
|---:|---|---|---:|---|
| 1 | `java:SXXXX` | `Clase.java` | 25 | Descripción mostrada por Sonar |
| 2 | `java:SYYYY` | `OtraClase.java` | 41 | Descripción mostrada por Sonar |

Una vez documentados el análisis y la captura inicial, estos cambios podrán incorporarse a `main`.

---

## 7. Resolución de las disconformidades y trabajo con Git

A partir del análisis inicial, el grupo debe revisar y resolver **todas** las disconformidades.

Para cada una de ellas:

1. selecciona el *issue* en la vista de SonarQube;
2. consulta la explicación de la regla;
3. identifica qué parte concreta del código la incumple;
4. plantea una solución antes de modificar el código;
5. aplica únicamente los cambios necesarios para esa corrección;
6. guarda el fichero;
7. vuelve a analizar el código;
8. comprueba que la disconformidad ha desaparecido y que no se han generado nuevos problemas;
9. actualiza la documentación correspondiente en `README_P1.md`; y
10. realiza un **commit independiente** con esa corrección.

### 7.1. Un commit por corrección

Cada corrección debe quedar identificada mediante un commit independiente en `main`.

Debe evitarse agrupar en un único commit varias correcciones no relacionadas.

Ejemplo de mensaje de commit:

```text
P1 - S4973 - Corregir comparación de cadenas
```

Otro ejemplo:

```text
P1 - S1128 - Eliminar import innecesario
```

Los mensajes genéricos como los siguientes no son adecuados:

```text
cambios
```

```text
correcciones sonar
```

```text
práctica
```

### 7.2. Autoría de las correcciones

Los dos miembros del grupo deben participar en la resolución de las disconformidades.

Cada participante realizará personalmente los commits correspondientes a las correcciones que haya desarrollado.

El autor registrado por Git debe coincidir con el miembro indicado como responsable en `README_P1.md`.

No se considerará acreditada la participación de un miembro únicamente porque su nombre aparezca escrito en el informe.

### 7.3. Secuencia recomendada para cada corrección

Antes de comenzar:

```bash
git pull --rebase origin main
```

Después de aplicar y comprobar una corrección:

```bash
git status
```

Añadir únicamente los ficheros relacionados con esa corrección, por ejemplo:

```bash
git add P1/proyecto/P1_INICIALES/src/ruta/Clase.java
git add P1/README_P1.md
```

Realizar el commit:

```bash
git commit -m "P1 - SXXXX - Descripción breve de la corrección"
```

Y publicar los cambios:

```bash
git push origin main
```

### 7.4. Relación entre disconformidad y commit

En `README_P1.md`, cada solución debe indicar:

- regla Sonar;
- localización;
- problema detectado;
- solución adoptada;
- nombre y apellidos del miembro responsable; y
- identificador abreviado del commit.

Por ejemplo:

```text
Commit: a1b2c3d
```

El identificador puede obtenerse mediante:

```bash
git log --oneline
```

### 7.5. Correcciones relacionadas

En algunos casos, una misma modificación atómica puede resolver varias disconformidades estrechamente relacionadas.

Cuando no sea razonable separar técnicamente esas correcciones:

- se realizará un único commit;
- en `README_P1.md` se indicarán todas las disconformidades resueltas por ese commit; y
- se explicará que corresponden a una misma modificación.

Esta excepción no debe utilizarse para agrupar arbitrariamente múltiples correcciones independientes.

### 7.6. Qué se considera una corrección válida

La solución debe actuar sobre la causa del problema en el código.

**No** se considera una corrección válida:

- desactivar la regla;
- excluir el fichero o la carpeta del análisis;
- utilizar mecanismos de supresión únicamente para ocultar el *issue*;
- marcar el problema como ignorado o aceptado;
- modificar la configuración de Sonar para evitar la detección; o
- eliminar de forma arbitraria una parte funcional del programa solo para hacer desaparecer la disconformidad.

Cuando la propia regla identifique **código muerto o innecesario**, su eliminación razonada sí constituye una corrección válida.

> Obtener **cero disconformidades** significa que el código cumple las reglas analizadas por la herramienta, pero **no demuestra por sí solo que el programa sea correcto**.

---

## 8. Análisis final

Cuando el grupo considere resueltas todas las disconformidades:

1. guarda todos los ficheros;
2. selecciona el **proyecto completo**;
3. ejecuta **SonarQube > Analyze**;
4. comprueba que no queda ninguna disconformidad pendiente con las reglas establecidas; y
5. realiza la captura final.

### 8.1. Captura final

La captura final debe mostrar simultáneamente y de forma legible:

- **el mismo nombre de proyecto** que aparece en la captura inicial; y
- la vista de SonarQube evidenciando que ya no aparecen disconformidades.

El fichero se guardará en:

```text
P1/imagenes/sonar_final.png
```

y se incorporará también a `README_P1.md`.

> La captura inicial y la captura final deben corresponder al mismo proyecto. No se debe crear un segundo proyecto para obtener la evidencia final.

---

## 9. Documentación en README_P1.md

El fichero:

```text
P1/README_P1.md
```

es el informe de la práctica.

Debe permitir reconstruir qué problemas se detectaron, qué solución se aplicó a cada uno y quién realizó cada modificación.

### 9.1. Identificación del grupo

Debe incluir:

- nombre y apellidos del primer miembro;
- nombre y apellidos del segundo miembro; y
- nombre exacto del proyecto Eclipse.

### 9.2. Evidencia inicial

Debe incluir la imagen:

```markdown
![Análisis inicial de SonarQube for Eclipse](imagenes/sonar_inicial.png)
```

### 9.3. Colección de disconformidades

Debe incluir una tabla que recoja **todas las disconformidades detectadas inicialmente**.

### 9.4. Solución individual de cada disconformidad

Cada disconformidad tendrá una sección propia donde se documentará:

- regla;
- localización;
- problema detectado;
- solución adoptada;
- responsable; y
- commit.

### 9.5. Resumen de correcciones

Se incluirá una tabla de resumen que permita relacionar rápidamente:

```text
disconformidad → responsable → commit → resultado
```

### 9.6. Evidencia final

Debe incluir:

```markdown
![Análisis final de SonarQube for Eclipse](imagenes/sonar_final.png)
```

El **Anexo A** contiene un esqueleto completo que puede copiarse para crear el documento.

---

## 10. Proyecto Eclipse final

Además de la documentación, el repositorio debe contener el proyecto Eclipse después de aplicar todas las correcciones.

Su ubicación será:

```text
P1/proyecto/P1_<INICIALES>/
```

El proyecto debe:

- ser un proyecto Java estándar de Eclipse;
- conservar el nombre utilizado en las capturas;
- contener el código final corregido;
- compilar correctamente;
- no ser un proyecto Maven;
- no contener `pom.xml`; y
- corresponder exactamente a la versión analizada en la captura final.

No debe subirse la carpeta de salida `bin/` ni otros ficheros generados temporalmente por el IDE si están correctamente excluidos mediante `.gitignore`.

---

## 11. Entrega

La entrega de la práctica se realizará mediante el **repositorio único del grupo**.

La carpeta `P1/` deberá estar completa en la rama:

```text
main
```

y contener:

```text
P1/
├── README_P1.md
├── imagenes/
│   ├── sonar_inicial.png
│   └── sonar_final.png
└── proyecto/
    └── P1_<INICIALES>/
        ├── .project
        ├── .classpath
        ├── .settings/
        └── src/
```

Además, el historial de `main` deberá permitir comprobar:

- el estado inicial de la práctica;
- las correcciones realizadas;
- un commit identificable por cada corrección independiente;
- la autoría de cada commit; y
- la participación de los dos miembros del grupo.

No se solicita un informe adicional fuera de `README_P1.md`.

La documentación de las disconformidades, sus soluciones y su trazabilidad Git queda centralizada en dicho fichero.

---

## 12. Criterios de evaluación

La práctica se considerará correctamente realizada cuando se compruebe que:

### 12.1. Organización del repositorio

- [ ] El grupo utiliza un único repositorio para las prácticas.
- [ ] Existe la carpeta `P1/`.
- [ ] Existe `P1/README_P1.md`.
- [ ] Las imágenes están dentro de `P1/imagenes/`.
- [ ] El proyecto final está dentro de `P1/proyecto/`.

### 12.2. Proyecto y análisis inicial

- [ ] El proyecto utiliza el nombre obligatorio `P1_<INICIALES>`.
- [ ] La captura inicial corresponde al código original.
- [ ] En la captura inicial se ve el nombre del proyecto.
- [ ] En la captura inicial se observan las disconformidades detectadas.
- [ ] El análisis se ha realizado localmente con SonarQube for Eclipse en modo standalone.
- [ ] Se ha mantenido la configuración de reglas establecida para la práctica.

### 12.3. Documentación

- [ ] En `README_P1.md` aparecen los nombres y apellidos de los dos miembros.
- [ ] Se incluye la captura inicial.
- [ ] Se han documentado todas las disconformidades observadas inicialmente.
- [ ] Cada disconformidad tiene documentada su solución.
- [ ] Cada solución identifica al miembro responsable.
- [ ] Cada solución identifica el commit correspondiente.
- [ ] Se incluye un resumen de correcciones.
- [ ] Se incluye la captura final.

### 12.4. Trabajo con Git

- [ ] Las correcciones se han realizado sobre `main`.
- [ ] Cada corrección independiente se encuentra en un commit independiente.
- [ ] Los mensajes de commit permiten identificar la corrección realizada.
- [ ] El autor de cada commit coincide con el responsable indicado en `README_P1.md`.
- [ ] Los dos miembros del grupo han participado mediante commits propios.
- [ ] No se ha reescrito el historial para ocultar o agrupar artificialmente las correcciones.

### 12.5. Resultado final

- [ ] Las disconformidades se han corregido modificando adecuadamente el código y no ocultando los resultados.
- [ ] La captura final utiliza exactamente el mismo nombre de proyecto.
- [ ] La captura final evidencia que no quedan disconformidades.
- [ ] El proyecto Eclipse final está incluido en el repositorio.
- [ ] El proyecto final corresponde al código mostrado en el análisis final.

> El objetivo evaluable no consiste únicamente en conseguir que desaparezcan los avisos de Sonar. El grupo debe ser capaz de **identificar el problema, justificar la solución aplicada y demostrar mediante el historial Git quién realizó cada corrección**.

---

## 13. Errores y dudas frecuentes

### No aparece la opción `SonarQube` al pulsar con el botón derecho

Comprueba que el plugin está instalado correctamente y reinicia Eclipse.

### Solo aparecen problemas del fichero que tengo abierto

Selecciona la raíz del proyecto y ejecuta expresamente **SonarQube > Analyze** para analizar el proyecto completo.

### A distintos grupos les aparecen resultados diferentes

Comprobad que:

- se está analizando exactamente el proyecto original;
- todos utilizan la versión del plugin establecida en el laboratorio;
- nadie ha modificado **Preferences > SonarQube > Rules Configuration**;
- no hay ficheros excluidos; y
- ningún proyecto está conectado a SonarQube Server o SonarQube Cloud.

### Después de corregir una disconformidad aparece otra nueva

Puede ser normal. Algunas reglas pasan a ser aplicables después de una modificación. Debe continuarse el ciclo:

```text
analizar → comprender → corregir → documentar → commit → volver a analizar
```

hasta que el proyecto completo quede sin disconformidades.

### Una modificación corrige dos disconformidades a la vez

Si ambas dependen realmente de la misma modificación atómica, pueden documentarse bajo el mismo commit indicando claramente qué disconformidades resuelve.

No deben utilizarse commits grandes para agrupar correcciones independientes.

### Sonar no muestra problemas pero el programa sigue teniendo un comportamiento incorrecto

También puede ocurrir. Una herramienta de análisis estático no garantiza la corrección total del software. Por ello debe combinarse con revisión del código y técnicas de prueba.

### Dos miembros intentan modificar `main` al mismo tiempo

Antes de comenzar una corrección, actualiza el repositorio:

```bash
git pull --rebase origin main
```

Si otra persona ha publicado cambios mientras trabajabas, integra esos cambios antes de realizar el `push`.

### El commit aparece con el nombre de otro compañero

Revisa la configuración local de Git:

```bash
git config user.name
git config user.email
```

En equipos compartidos no debe utilizarse la configuración global de otro usuario.

---

# Anexo A. Esqueleto de README_P1.md

El siguiente contenido puede utilizarse como punto de partida para:

```text
P1/README_P1.md
```

````markdown
# Práctica 1 — Revisiones estáticas de código con SonarQube for Eclipse

## 1. Miembros del grupo

| Miembro | Nombre y apellidos |
|---|---|
| Alumno/a 1 | NOMBRE Y APELLIDOS |
| Alumno/a 2 | NOMBRE Y APELLIDOS |

**Nombre del proyecto Eclipse:** `P1_INICIALES`

---

## 2. Análisis inicial

Antes de realizar ninguna modificación sobre el código proporcionado se ha ejecutado el análisis estático del proyecto utilizando **SonarQube for Eclipse** con su configuración por defecto.

### Captura inicial

![Análisis inicial de SonarQube for Eclipse](imagenes/sonar_inicial.png)

---

## 3. Disconformidades detectadas

En el análisis inicial se han identificado las siguientes disconformidades:

| Nº | Regla Sonar | Archivo | Línea | Disconformidad |
|---:|---|---|---:|---|
| 1 | `java:SXXXX` | `Clase.java` | XX | Descripción de la disconformidad |
| 2 | `java:SXXXX` | `Clase.java` | XX | Descripción de la disconformidad |
| 3 | `java:SXXXX` | `Clase.java` | XX | Descripción de la disconformidad |

> Deben incluirse **todas las disconformidades observadas en el análisis inicial**.

---

## 4. Soluciones adoptadas

### Disconformidad 1 — `java:SXXXX`

**Localización:** `src/.../Clase.java`, línea XX  
**Responsable:** NOMBRE Y APELLIDOS  
**Commit:** `abcdef1`

**Problema detectado**

Descripción breve del problema indicado por SonarQube for Eclipse.

**Solución adoptada**

Descripción de la modificación realizada para resolver la disconformidad.

---

### Disconformidad 2 — `java:SXXXX`

**Localización:** `src/.../Clase.java`, línea XX  
**Responsable:** NOMBRE Y APELLIDOS  
**Commit:** `abcdef2`

**Problema detectado**

Descripción breve del problema indicado por SonarQube for Eclipse.

**Solución adoptada**

Descripción de la modificación realizada para resolver la disconformidad.

---

### Disconformidad 3 — `java:SXXXX`

**Localización:** `src/.../Clase.java`, línea XX  
**Responsable:** NOMBRE Y APELLIDOS  
**Commit:** `abcdef3`

**Problema detectado**

Descripción breve del problema indicado por SonarQube for Eclipse.

**Solución adoptada**

Descripción de la modificación realizada para resolver la disconformidad.

---

## 5. Resumen de las correcciones

| Nº | Regla Sonar | Responsable | Commit | Resultado |
|---:|---|---|---|---|
| 1 | `java:SXXXX` | Nombre y apellidos | `abcdef1` | Resuelta |
| 2 | `java:SXXXX` | Nombre y apellidos | `abcdef2` | Resuelta |
| 3 | `java:SXXXX` | Nombre y apellidos | `abcdef3` | Resuelta |

---

## 6. Análisis final

Una vez realizadas todas las modificaciones se ha vuelto a ejecutar el análisis del proyecto completo con **SonarQube for Eclipse**.

### Captura final

![Análisis final de SonarQube for Eclipse](imagenes/sonar_final.png)

La captura final permite comprobar que se está analizando el mismo proyecto utilizado en la captura inicial y que ya no quedan disconformidades pendientes.

---

## 7. Proyecto final

La versión final del proyecto Eclipse se encuentra en:

```text
P1/proyecto/P1_INICIALES/
```

El proyecto incluido en esta carpeta contiene las modificaciones correspondientes a las soluciones documentadas anteriormente y coincide con la versión sobre la que se ha realizado la captura final.

---

## 8. Comprobación de la entrega

- [ ] El nombre del proyecto sigue el formato establecido: `P1_INICIALES`.
- [ ] Se identifican los dos miembros del grupo.
- [ ] Se incluye la captura del análisis inicial.
- [ ] Se han documentado todas las disconformidades inicialmente detectadas.
- [ ] Cada solución está asociada a un commit identificable en `main`.
- [ ] Se identifica qué miembro del grupo realizó cada corrección.
- [ ] Los dos miembros han participado mediante commits propios.
- [ ] Se incluye la captura del análisis final.
- [ ] La captura final permite comprobar que no quedan disconformidades.
- [ ] Se ha incorporado el proyecto Eclipse final dentro de `P1/proyecto/`.
- [ ] El proyecto final corresponde al código analizado en la captura final.
````

