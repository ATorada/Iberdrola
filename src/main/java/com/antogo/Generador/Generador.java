package com.antogo.Generador;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;

public class Generador {

    //~~Variables Protected~~
    int contador;
    int contadorCups;

    //~~Variables Static y públicas~~
    //Cantidad de registros
    public static int cantidad;
    //Conexión y colecciones
    public static MongoClient mongo_client;
    public static MongoDatabase mongo_database;
    public static MongoCollection<Document> contratos_collection;
    public static MongoCollection<Document> cliente_collection;
    public static MongoCollection<Document> consumos_collection;
    static ArrayList<ArrayList<ArrayList<Double>>> PrecioMesDiasHoras;
    public static ArrayList<Document> Facturas = new ArrayList();

    //Constructor que recibe una cantidad y un contador
    public Generador(int cantidad, int contador) {
        //Se inicializa la cantidad con la que se va a trabajar
        this.cantidad = cantidad;
        //El contador que se va a usar en las iteraciones
        this.contador = contador;
        //El contador que se usará en las iteraciones específicas del cups
        contadorCups = contador;
        //Método que carga las conexiones principales de la base de datos
        cargar_conexiones();
    }

    //Método que carga las conexiones con la base de datos
    public static void cargar_conexiones() {
        //Desactiva el Logger para que no salga mucho texto en rojo cada vez que ejecutamos la aplicación
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);

        //Se conecta al servicio y a la base de datos
        mongo_client = new MongoClient("localhost", 27017);
        mongo_database = mongo_client.getDatabase("consumo");

        //Se obtienen las principales colecciones
        contratos_collection = mongo_database.getCollection("contrato");
        cliente_collection = mongo_database.getCollection("cliente");
        consumos_collection = mongo_database.getCollection("consumo_enero");
    }

    //Método que crea y borra la colección basuras
    @Deprecated
    //Está deprecated porque ahora genero factura_<mes>, pero en caso de error se utiliza este en las inserciones
    public static void preparar_facturas() {

        try {
            mongo_database.getCollection("facturas").drop();
        } catch (Exception ex) {

        }
        mongo_database.createCollection("facturas");
    }

    //Método que genera y devuelve un ArrayList de Documentos de tipo cliente
    public ArrayList<Document> generar_clientes() {
        //Crea el ArrayList
        ArrayList<Document> clientes = new ArrayList<>();
        //Itera por la cantidad de clientes que tenga que crear por cada ronda
        for (int i = 1; i <= cantidad; i++) {
            //Se establecen los datos en base a contador, el cual se incrementa después de cada round
            Document cliente = new Document();
            cliente.append("_id", new ObjectId());
            cliente.append("DNI", "DNI" + contador);
            cliente.append("nombre", "Nombre" + contador);
            cliente.append("telefono", "Telefono" + contador);
            cliente.append("correo", "correo" + contador + "@prueba.com");
            cliente.append("direccion", "Direccion" + contador);
            clientes.add(cliente);
            contador++;

        }
        //Devuelve los clientes
        return clientes;
    }

    //Método que genera y devuelve un ArrayList de documentos de tipo consumo
    public ArrayList<Document> generar_consumos() {
        //Crea el ArrayList
        ArrayList<Document> consumos = new ArrayList<>();
        //Genera tantos consumos como cantidad por iteración
        for (int i = 1; i <= cantidad; i++) {
            Document consumo = new Document();

            consumo.append("_id", new ObjectId());
            ArrayList<Document> fecha_horas_ArrayList = new ArrayList<>();

            //Generar 31 días de una @Deprecated porque consume mucho
            //(Si se descomenta habrá que generar únicamente insertarUnDia en cada mes)
            //for (int j = 0; j < 31; j++) {
            Document fecha_horas = new Document();
            ArrayList<Integer> horas = new ArrayList();
            horas = generar_horas();
            fecha_horas.append("fecha", new Date());
            fecha_horas.append("horas", horas);
            fecha_horas_ArrayList.add(fecha_horas);
            //}
            consumo.append("consumo_fecha", fecha_horas_ArrayList);
            consumos.add(consumo);
        }
        //Devuelve los consumos
        return consumos;
    }

    //Método que se encarga de devolver generar un ArrayList de enteros con el consumo de cada hora (24 horas)
    private ArrayList<Integer> generar_horas() {
        //Crea el ArrayList
        ArrayList<Integer> horas = new ArrayList<>();
        //Crea un entero aleatorio y lo añade
        for (int i = 0; i < 24; i++) {
            horas.add(getRandom(3500));
        }
        //Devuelve las horas
        return horas;
    }

    //Método que se encarga de devolver y generar un ArrayList de Documentos de tipo Contrato
    public ArrayList<Document> generar_contratos() {
        //Crea el ArrayList
        ArrayList<Document> contratos = new ArrayList<>();
        //Genera una cantidad de contratos y el Cups se aumenta de manera paralela con un contador diferente
        for (int i = 1; i <= cantidad; i++) {
            Document contrato = new Document();
            contrato.append("_id", new ObjectId());
            contrato.append("cups", contadorCups);
            contrato.append("año", "2022");
            contratos.add(contrato);
            //Se aumenta el cups
            contadorCups++;
        }
        //Devuelve los contratos
        return contratos;
    }

    //Método que genera y devuelve un ArrayList con un ArrayList de Documentos con el Primer Insert generado
    public ArrayList<ArrayList<Document>> generar_primerinsert() {
        //Genera los ArrayLists
        ArrayList<ArrayList<Document>> inserts = new ArrayList<>();
        ArrayList<Document> clientes = generar_clientes();
        ArrayList<Document> contratos = generar_contratos();
        //ArrayList<Document> consumos = generar_consumos();
        //Bucle de cantidad de inserts por insert many que genera cada documento y sus relaciones
        for (int i = 0; i < cantidad; i++) {
            //¡Atención! El texto comentado genera los consumos @Deprecated para ser fiel al requisito
            Document contrato = contratos.get(i);
            Document cliente_contrato = new Document();
            //Document consumoMes_contrato = new Document();

            cliente_contrato.append("_id", new ObjectId(clientes.get(i).get("_id").toString()));
            cliente_contrato.append("DNI", clientes.get(i).get("DNI").toString());
            //consumoMes_contrato.append("_id", new ObjectId(consumos.get(i).get("_id").toString()));
            ArrayList<Document> array_consumos = new ArrayList();
            //array_consumos.add(consumoMes_contrato);
            contrato.append("cliente", cliente_contrato);
            contrato.append("consumo_mes", array_consumos);
        }
        inserts.add(clientes);
        //inserts.add(consumos);
        inserts.add(contratos);
        //Devuelve los inserts a hacer
        return inserts;
    }

    //Método que se encarga de obtener los consumos a partir de un punto
    public ArrayList<Document> obtener_consumos(int skip) {

        //Crea un ArrayList de consumos
        ArrayList<Document> consumos = new ArrayList();

        //Obtiene los X cantidad de consumos a partir de Y punto
        FindIterable<Document> iterable_consumos = consumos_collection.find().limit(cantidad).skip(skip);
        for (Document document : iterable_consumos) {
            consumos.add(document);
        }
        //Los devuelve
        return consumos;

    }

    //Método que devuelve un ArrayList de un ArrayList de documentos que contiene el día a insertar y el documento que actualizar
    public ArrayList<ArrayList<Document>> generar_updatesDiarios(int cantidadPorRegistro) {

        //Genera un ArrayList de ArrayList de Documentos
        ArrayList<ArrayList<Document>> updatesDiarios = new ArrayList<>();
        //updates.get(n) se añadirá a documentos.get(n) y siendo n el i del bucle
        //ArrayList con cada día a añadir
        ArrayList<Document> updates = new ArrayList<>();
        //ArrayList que obtiene una cantidad de consumos
        ArrayList<Document> documentos = obtener_consumos(cantidadPorRegistro);
        //Hace un for por la cantidad máxima de inserts por ronda
        for (int i = 0; i < cantidad; i++) {
            //Crea el documento del dia
            Document dia = new Document();
            //Crea el documento de update
            Document updateQuery = new Document();
            //Le añade la información al día
            dia.append("consumo_fecha", new BasicDBObject("fecha", new Date()).append("horas", generar_horas()));
            //Establece la actualización con $push, y la actualización es añadiendo día
            updateQuery.append("$push", dia);
            //Lo añade al ArrayList de updates
            updates.add(updateQuery);
        }
        //Guarda todos los Documentos en orden de inseción
        updatesDiarios.add(documentos);
        //Guarda todos los Updates en orden de inserción
        updatesDiarios.add(updates);
        //Devuelve los documentos a actualizar y los updates
        return updatesDiarios;
    }

    //Método que devuelve un random entre un número
    public static int getRandom(int max) {
        return (int) (Math.random() * max);
    }

    //Método que devuelve un random entre un máximo y un mínimo
    public static int getRandom(int min, int max) {
        Random rnd = new Random();
        return rnd.nextInt((max - min) + 1) + min;
    }

    //Método que genera las tasas del año, es decir las horas de un día de un mes
    public static void Generar_Tasas_Año() {
        PrecioMesDiasHoras = new ArrayList();
        ArrayList<ArrayList<Double>> PrecioHorasDia = new ArrayList();
        ArrayList<Double> PrecioHoras;
        for (int i = 0; i < 12; i++) {

            for (int j = 0; j < 31; j++) {
                PrecioHoras = new ArrayList();
                for (int y = 0; y < 24; y++) {
                    PrecioHoras.add(((double) getRandom(3681, 25234)) / 100000000);
                }
                PrecioHorasDia.add(PrecioHoras);
            }
            PrecioMesDiasHoras.add(PrecioHorasDia);
        }

    }

    //Método que se encarga de devolver los consumos de un mes pasado por parámetro
    public static ArrayList<ArrayList<Integer>> Devolver_Consumos(ObjectId idConsumoMes) {
        //System.out.println(idConsumoMes);
        ArrayList<ArrayList<Integer>> DiasConsumos = new ArrayList();
        ArrayList<Integer> Consumos;

        //Devuelve los consumos de todas las fechas de un mes
        BasicDBObject query = new BasicDBObject();
        query.put("_id", idConsumoMes);

        FindIterable<Document> consumosFechaHora = consumos_collection.find(query);

        //Recorre las fechas de cada mes
        for (Document consumos : consumosFechaHora) {
            //System.out.println(consumos);
            //Va guardando los consumos
            List<Document> lista_Dias = (List<Document>) consumos.get("consumo_fecha");
            for (Document Dia : lista_Dias) {
                List<Integer> lista_Horas = (List<Integer>) Dia.get("horas");
                Consumos = new ArrayList();
                for (Integer Consumo : lista_Horas) {

                    Consumos.add(Consumo);
                }
                DiasConsumos.add(Consumos);
            }
        }
        //Los Devuelve
        return DiasConsumos;
    }

    //Método que se encarga de devolver una factura en base a un cups, un mes y si es solo una factura se le pasa un true que mostrará el texto en consola
    public static void Mostrar_Factura_Periodo(int cups, int mes, boolean mostrar) {

        try {
            //Hace una consula para el cups
            BasicDBObject query = new BasicDBObject();
            query.put("cups", cups);

            //Con un switch establece la colección de consumos variando por el mes del que queremos obtener los consumos
            switch (mes) {
                case 0:
                    Generador.consumos_collection = Generador.mongo_database.getCollection("consumo_enero");
                    break;
                case 1:
                    Generador.consumos_collection = Generador.mongo_database.getCollection("consumo_febrero");
                    break;
                case 2:
                    Generador.consumos_collection = Generador.mongo_database.getCollection("consumo_marzo");
                    break;
                case 3:
                    Generador.consumos_collection = Generador.mongo_database.getCollection("consumo_abril");
                    break;
                case 4:
                    Generador.consumos_collection = Generador.mongo_database.getCollection("consumo_mayo");
                    break;
                case 5:
                    Generador.consumos_collection = Generador.mongo_database.getCollection("consumo_junio");
                    break;
                case 6:
                    Generador.consumos_collection = Generador.mongo_database.getCollection("consumo_julio");
                    break;
                case 7:
                    Generador.consumos_collection = Generador.mongo_database.getCollection("consumo_agosto");
                    break;
                case 8:
                    Generador.consumos_collection = Generador.mongo_database.getCollection("consumo_septiembre");
                    break;
                case 9:
                    Generador.consumos_collection = Generador.mongo_database.getCollection("consumo_octubre");
                    break;
                case 10:
                    Generador.consumos_collection = Generador.mongo_database.getCollection("consumo_noviembre");
                    break;
                case 11:
                    Generador.consumos_collection = Generador.mongo_database.getCollection("consumo_diciembre");
                    break;
            }

            //Obtiene el único contrato con ese consumo
            Document contrato = contratos_collection.find(query).first();

            //Obtiene el consumo de ese mes
            Document consumoEncontrado = new Document();
            List<Document> lista_consumos = (List<Document>) contrato.get("consumo_mes");
            consumoEncontrado = lista_consumos.get(mes);

            //Instanciamos variables para los tramos horarios
            int consumo_valle = 0;
            int consumo_punta = 0;
            int consumo_llano = 0;

            //Método que obtiene todos los consumos del consumo de un mes pasado por parámetro
            ArrayList<ArrayList<Integer>> consumos = Devolver_Consumos(new ObjectId(consumoEncontrado.get("_id").toString()));
            //System.out.println(consumos.get(0));
            //Consumo de cada día
            ArrayList<Integer> consumos_dia = new ArrayList();
            //Precio de cada día
            ArrayList<Double> precios_dia = new ArrayList();
            //Me sirve para formatear el decimal a 4 dígitos
            DecimalFormat df = new DecimalFormat("0.0000");
            //Consumo total del periodo
            int consumo_total_periodo = 0;
            //Precio total del periodo
            double precio_total_periodo = 0;
            //System.out.println(consumos.size());
            //For por la cantidad de consumo de cada día
            for (int i = 0; i < consumos.size(); i++) {
                if (mostrar) {
                    System.out.println("      ------ Día " + (i + 1) + " ------ \n");
                }
                //Consumo total del dia
                int consumo_total_diario = 0;
                //Precio total del dia
                double precio_total_diario = 0;
                //Por cada consumo reviso en que hora está y lo añado a un tramo
                for (int j = 0; j < consumos.get(i).size(); j++) {
                    if (j >= 0 && j <= 7) {
                        consumo_valle += consumos.get(i).get(j);
                    } else if ((j >= 10 && j <= 13) || (j >= 18 && j <= 21)) {
                        consumo_punta += consumos.get(i).get(j);
                    } else {
                        consumo_llano += consumos.get(i).get(j);
                    }
                    if (mostrar) {
                        System.out.println("          - Hora " + j + " -" + "\n        Consumo: " + consumos.get(i).get(j) + "W\n" + "   Precio Hora: " + df.format(PrecioMesDiasHoras.get(mes).get(i).get(j) * 1000) + "€/kWh\n");
                    }
                    //Añado a las variables su respectivo valor
                    consumo_total_diario += consumos.get(i).get(j);
                    consumo_total_periodo += consumos.get(i).get(j);
                    precio_total_diario += (consumos.get(i).get(j) * PrecioMesDiasHoras.get(mes).get(i).get(j));
                    precio_total_periodo += (consumos.get(i).get(j) * PrecioMesDiasHoras.get(mes).get(i).get(j));
                }
                //Le añado a consumos los consumos de cada día
                consumos_dia.add(consumo_total_diario);
                //Le añado a precios los precios de cada día
                precios_dia.add(Math.round(precio_total_diario * 100) / 100d);
                if (mostrar) {
                    System.out.println("~ Consumo Total Diario: " + consumo_total_diario + "W ~\n");
                    System.out.println("~ Precio Total Diario: " + df.format(precio_total_diario) + "€ ~\n");
                }
            }
            //Redondeo el precio total
            precio_total_periodo = Math.round(precio_total_periodo * 100) / 100d;

            //Cálculo de los impuestos variables
            double precio_punta_variable = ((consumo_punta / 1000) * 0.077191);
            double precio_llano_variable = ((consumo_llano / 1000) * 0.017497);
            double precio_valle_variable = ((consumo_valle / 1000) * 0.003784);
            //Cálculo de los impuestos fijos en base a un contrato de 3.45kw
            double precio_punta_llano_fijo = (3.45 * (22.988256 + 4.970533) * consumos.size() / 365);
            double precio_valle_fijo = (3.45 * (0.938890 + 0.319666) * consumos.size() / 365);
            //Total sin impuestos
            double total_sin_impuestos = Math.round((precio_valle_fijo + precio_punta_llano_fijo + precio_valle_variable + precio_llano_variable + precio_punta_variable) * 100) / 100d;
            //Total con impuestos
            double total_impuestos = Math.round((total_sin_impuestos + (total_sin_impuestos * 0.05)) * 100) / 100d;

            if (mostrar) {
                System.out.println("~ CONSUMO TOTAL PERIODO: " + ((double) consumo_total_periodo) / 1000 + "kW ~\n");
                System.out.println("~ PRECIO TOTAL PERIODO: " + df.format(precio_total_periodo) + "€ ~\n");
                System.out.println("~ PRECIO TOTAL PERIODO (Con impuestos): " + df.format((precio_total_periodo + total_impuestos)) + "€ ~\n");
            }
            //Genero el documento de la factura y lo guardo en una variable global
            Generar_Documentos_Factura_Periodo(cups, mes, consumos.size(), precio_total_periodo, precio_total_periodo + total_impuestos, ((double) consumo_total_periodo) / 1000, precios_dia, consumos_dia);
        } catch (Exception e) {

            //System.out.println("No hay consumos en ese mes");
        }
    }

    public static void Generar_Documentos_Factura_Periodo(int cups, int mes, int periodo, double precio_total, double precio_total_impuestos, double consumo_total, ArrayList<Double> precios_dia, ArrayList<Integer> consumos_dia) {

        String nombre_mes = null;

        //Guardo en una variable el mes de la factura
        switch (mes) {
            case 0:
                nombre_mes = "Enero";
                break;
            case 1:
                nombre_mes = "Febrero";
                break;
            case 2:
                nombre_mes = "Marzo";
                break;
            case 3:
                nombre_mes = "Abril";
                break;
            case 4:
                nombre_mes = "Mayo";
                break;
            case 5:
                nombre_mes = "Junio";
                break;
            case 6:
                nombre_mes = "Julio";
                break;
            case 7:
                nombre_mes = "Agosto";
                break;
            case 8:
                nombre_mes = "Septiembre";
                break;
            case 9:
                nombre_mes = "Octubre";
                break;
            case 10:
                nombre_mes = "Noviembre";
                break;
            case 11:
                nombre_mes = "Diciembre";
                break;
        }

        //Genero el documento factura y le añado los datos
        Document factura = new Document();
        factura.append("cups", cups);
        factura.append("mes", nombre_mes);
        factura.append("periodo", periodo);
        factura.append("precio_total", precio_total);
        factura.append("precio_total_impuestos", Math.round(precio_total_impuestos * 100) / 100d);
        factura.append("consumo_total", consumo_total);
        factura.append("precios_dia", precios_dia);
        factura.append("consumos_dia", consumos_dia);
        //Añado la factura a las facturas
        Facturas.add(factura);
    }

    //Borra un contrato y sus consumos en base a su cups
    public static void borrar_consumos_contrato(int cups) {

        //Hace una query en base al cups
        try {
            BasicDBObject query = new BasicDBObject();
            query.put("cups", cups);

            //Obtiene su único contrato, el cliente al que está asociado y todos sus consumos de cada mes
            Document contrato = contratos_collection.find(query).first();

            Document cliente = (Document) contrato.get("cliente");

            List<Document> lista_consumos = (List<Document>) contrato.get("consumo_mes");
            //Borra el contrato
            System.out.println("\nBorra el contrato: " + contrato);
            contratos_collection.findOneAndDelete(contrato);

            //Borra sus consumos
            for (Document consumo : lista_consumos) {
                System.out.println("Y su consumo: " + consumo);
                consumos_collection.findOneAndDelete(consumo);
            }

            //Obtiene el DNI del cliente y obtiene sus contratos restantes
            BasicDBObject queryCliente = new BasicDBObject();
            queryCliente.put("cliente.DNI", cliente.get("DNI"));
            String DNICliente = cliente.get("DNI").toString();
            FindIterable<Document> contratosCliente = contratos_collection.find(queryCliente);

            //Los muestra
            int count = 0;
            for (Document contratoCliente : contratosCliente) {
                System.out.println(contratoCliente);
                count++;
            }
            //Si el contador no ha encontrado ningún otro contrato, borra el cliente
            if (count == 0) {
                System.out.println("El cliente no tiene más contratos, borrándolo.");
                System.out.println("Cliente borrado: " + DNICliente);
                borrar_contratos_consumos_cliente(DNICliente);
            }

        } catch (Exception e) {
            System.out.println("No existe ese contrato");
        }

    }

    //Método que borra los contratos y los consumos de un cliente
    public static void borrar_contratos_consumos_cliente(String cliente) {

        try {
            //Consulta que obtiene el DNI del cliente
            BasicDBObject query = new BasicDBObject();
            query.put("cliente.DNI", cliente);

            //Obtiene sus contratos
            FindIterable<Document> lista_contratos = contratos_collection.find(query);
            System.out.println("\nBorra el cliente: " + cliente);
            //Por cada contrato obtiene sus consumos y los borra
            for (Document contrato : lista_contratos) {
                //System.out.println("Borra del cliente: " + cliente + "\nEl contrato: " + contrato);

                //Obtiene los consumos
                List<Document> lista_consumos = (List<Document>) contrato.get("consumo_mes");
                //Borra el contrato
                System.out.println("Borra su contrato:" + contrato);
                contratos_collection.findOneAndDelete(contrato);
                //Borra los consumos
                for (Document consumo : lista_consumos) {
                    System.out.println("Y su consumo: " + consumo);
                    consumos_collection.findOneAndDelete(consumo);
                }
            }
            //Por último borra el cliente
            BasicDBObject DNIQuery = new BasicDBObject();
            DNIQuery.put("DNI", cliente);
            cliente_collection.findOneAndDelete(DNIQuery);
        } catch (Exception e) {
            System.out.println("Ese cliente no existe");
        }

    }

    //Método que devuelve un ArrayList de documentos de tipo contrato a partir de X cantidad
    public ArrayList<Document> obtener_contatos(int skip) {
        //Crea el ArrayList de contratos
        ArrayList<Document> contratos = new ArrayList();
        //Obtiene X cantidad de contratos en base al Límite Y pasado por parámetro
        FindIterable<Document> iterable_contratos = contratos_collection.find().limit(cantidad).skip(skip);
        for (Document document : iterable_contratos) {
            contratos.add(document);
        }

        //mongo_client.close();
        //Los devuelve
        return contratos;
    }

}
