Use Listas comunes en lugar de otro TAD para guardar atributos porque son mas faciles de iterar y 
la mayor ventaja de usar otro tipo de TAD es la velocidad de busqueda, pero como en este caso si necesitamos 
vistar todos los elementos esa ventaja se pierde.

Para cargar los pilotos hay que poner un txt en la carpeta csv que se llame drivers(o modificar el codigo en el 
constructor de 'elTweeter', linea 61) y poner nombre espacio apellido y solo uno por linea.

Para cargar los datos hay que poner un csv en la carpeta csv que se llame f1_dataset_test(o modificar el codigo en el 
csv reader, linea 25)

Tiempo de carga de los datos: 189538 milisegundos
Tiempo por funcion: 
1- Milisegundos de duracion: 63863 
2- Milisegundos de duracion: 22342
3- Milisegundos de duracion: 52773 
4- Milisegundos de duracion: 24768
5- Milisegundos de duracion: 10867
6- Milisegundos de duracion: 76584 (Buscando la palabra 'car')

github: https://github.com/mtvaldez/Obligatorio2023.git