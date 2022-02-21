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

    public static int cantidad;
    int contador;
    int contadorCups;

    public static MongoClient mongo_client;
    public static MongoDatabase mongo_database;
    public static MongoCollection<Document> contratos_collection;
    public static MongoCollection<Document> cliente_collection;
    public static MongoCollection<Document> consumos_collection;
    static ArrayList<ArrayList<ArrayList<Double>>> PrecioMesDiasHoras;

    public static ArrayList<Document> Facturas = new ArrayList();

    public Generador(int cantidad, int contador) {
        this.cantidad = cantidad;
        this.contador = contador;
        contadorCups = contador;
        cargar_conexiones();
    }

    public static void cargar_conexiones() {
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);

        mongo_client = new MongoClient("localhost", 27017);
        mongo_database = mongo_client.getDatabase("consumo");

        contratos_collection = mongo_database.getCollection("contrato");
        cliente_collection = mongo_database.getCollection("cliente");
        consumos_collection = mongo_database.getCollection("consumo_enero");
    }

    public static void preparar_facturas() {

        try {
            mongo_database.getCollection("facturas").drop();
        } catch (Exception ex) {

        }
        mongo_database.createCollection("facturas");
    }

    public ArrayList<Document> generar_clientes() {
        ArrayList<Document> clientes = new ArrayList<>();
        for (int i = 1; i <= cantidad; i++) {
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
        return clientes;
    }

    public ArrayList<Document> generar_consumos() {
        ArrayList<Document> consumos = new ArrayList<>();
        for (int i = 1; i <= cantidad; i++) {
            Document consumo = new Document();

            consumo.append("_id", new ObjectId());
            ArrayList<Document> fecha_horas_ArrayList = new ArrayList<>();

            //Generar 31 días de una
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
        return consumos;
    }

    private ArrayList<Integer> generar_horas() {
        ArrayList<Integer> horas = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            horas.add(getRandom(3500));
        }
        return horas;
    }

    public ArrayList<Document> generar_contratos() {

        ArrayList<Document> contratos = new ArrayList<>();
        for (int i = 1; i <= cantidad; i++) {
            Document contrato = new Document();
            contrato.append("_id", new ObjectId());
            contrato.append("cups", contadorCups);
            contrato.append("año", "2022");
            contratos.add(contrato);
            contadorCups++;
        }
        return contratos;
    }

    public ArrayList<ArrayList<Document>> generar_primerinsert() {
        ArrayList<ArrayList<Document>> inserts = new ArrayList<>();
        ArrayList<Document> clientes = generar_clientes();
        ArrayList<Document> contratos = generar_contratos();
        //ArrayList<Document> consumos = generar_consumos();
        for (int i = 0; i < cantidad; i++) {
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
        return inserts;
    }

    public ArrayList<Document> generar_consumos_nuevos() {
        return null;
    }

    public ArrayList<Document> obtener_consumos(int skip) {

        ArrayList<Document> consumos = new ArrayList();

        FindIterable<Document> iterable_consumos = consumos_collection.find().limit(cantidad).skip(skip);
        for (Document document : iterable_consumos) {
            consumos.add(document);
        }

        return consumos;

    }

    public ArrayList<ArrayList<Document>> generar_updatesDiarios(int cantidadPorRegistro) {

        ArrayList<ArrayList<Document>> updatesDiarios = new ArrayList<>();
        ArrayList<Document> updates = new ArrayList<>();
        ArrayList<Document> documentos = obtener_consumos(cantidadPorRegistro);
        for (int i = 0; i < cantidad; i++) {
            Document listItem = new Document();
            Document updateQuery = new Document();
            listItem.append("consumo_fecha", new BasicDBObject("fecha", new Date()).append("horas", generar_horas()));
            updateQuery.append("$push", listItem);
            updates.add(updateQuery);
        }

        updatesDiarios.add(documentos);
        updatesDiarios.add(updates);

        return updatesDiarios;
    }

    public static int getRandom(int max) {
        return (int) (Math.random() * max);
    }

    public static int getRandom(int min, int max) {
        Random rnd = new Random();
        return rnd.nextInt((max - min) + 1) + min;
    }

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

    public static ArrayList<ArrayList<Integer>> Devolver_Consumos(ObjectId idConsumoMes) {
        //System.out.println(idConsumoMes);
        ArrayList<ArrayList<Integer>> DiasConsumos = new ArrayList();
        ArrayList<Integer> Consumos;

        BasicDBObject query = new BasicDBObject();
        query.put("_id", idConsumoMes);

        FindIterable<Document> consumosFechaHora = consumos_collection.find(query);

        for (Document consumos : consumosFechaHora) {
            //System.out.println(consumos);
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
        return DiasConsumos;
    }

    public static void Mostrar_Factura_Periodo(int cups, int mes, boolean mostrar) {

        try {
            BasicDBObject query = new BasicDBObject();
            query.put("cups", cups);

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

            Document contrato = contratos_collection.find(query).first();

            //System.out.println(contrato);
            Document consumoEncontrado = new Document();
            List<Document> lista_consumos = (List<Document>) contrato.get("consumo_mes");
            consumoEncontrado = lista_consumos.get(mes);

            int consumo_valle = 0;
            int consumo_punta = 0;
            int consumo_llano = 0;

            ArrayList<ArrayList<Integer>> consumos = Devolver_Consumos(new ObjectId(consumoEncontrado.get("_id").toString()));
            //System.out.println(consumos.get(0));
            ArrayList<Integer> consumos_dia = new ArrayList();
            ArrayList<Double> precios_dia = new ArrayList();
            DecimalFormat df = new DecimalFormat("0.0000");
            int consumo_total_periodo = 0;
            double precio_total_periodo = 0;
            System.out.println(consumos.size());
            for (int i = 0; i < consumos.size(); i++) {
                if (mostrar) {
                    System.out.println("      ------ Día " + (i + 1) + " ------ \n");
                }
                int consumo_total_diario = 0;
                double precio_total_diario = 0;
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
                    consumo_total_diario += consumos.get(i).get(j);
                    consumo_total_periodo += consumos.get(i).get(j);
                    precio_total_diario += (consumos.get(i).get(j) * PrecioMesDiasHoras.get(mes).get(i).get(j));
                    precio_total_periodo += (consumos.get(i).get(j) * PrecioMesDiasHoras.get(mes).get(i).get(j));
                }
                consumos_dia.add(consumo_total_diario);
                precios_dia.add(Math.round(precio_total_diario * 100) / 100d);
                if (mostrar) {
                    System.out.println("~ Consumo Total Diario: " + consumo_total_diario + "W ~\n");
                    System.out.println("~ Precio Total Diario: " + df.format(precio_total_diario) + "€ ~\n");
                }
            }
            precio_total_periodo = Math.round(precio_total_periodo * 100) / 100d;
            double precio_punta_variable = ((consumo_punta / 1000) * 0.077191);
            double precio_llano_variable = ((consumo_llano / 1000) * 0.017497);
            double precio_valle_variable = ((consumo_valle / 1000) * 0.003784);

            double precio_punta_llano_fijo = (3.45 * (22.988256 + 4.970533) * consumos.size() / 365);
            double precio_valle_fijo = (3.45 * (0.938890 + 0.319666) * consumos.size() / 365);

            double total_sin_impuestos = Math.round((precio_valle_fijo + precio_punta_llano_fijo + precio_valle_variable + precio_llano_variable + precio_punta_variable) * 100) / 100d;
            double total_impuestos = Math.round((total_sin_impuestos + (total_sin_impuestos * 0.05)) * 100) / 100d;

            if (mostrar) {
                System.out.println("~ CONSUMO TOTAL PERIODO: " + ((double) consumo_total_periodo) / 1000 + "kW ~\n");
                System.out.println("~ PRECIO TOTAL PERIODO: " + df.format(precio_total_periodo) + "€ ~\n");
                System.out.println("~ PRECIO TOTAL PERIODO (Con impuestos): " + df.format((precio_total_periodo + total_impuestos)) + "€ ~\n");
            }
            Generar_Documentos_Factura_Periodo(cups, mes, consumos.size(), precio_total_periodo, precio_total_periodo + total_impuestos, ((double) consumo_total_periodo) / 1000, precios_dia, consumos_dia);
        } catch (Exception e) {
            //System.out.println("No hay consumos en ese mes");
        }
    }

    public static void Generar_Documentos_Factura_Periodo(int cups, int mes, int periodo, double precio_total, double precio_total_impuestos, double consumo_total, ArrayList<Double> precios_dia, ArrayList<Integer> consumos_dia) {

        String nombre_mes = null;

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

        Document factura = new Document();
        factura.append("cups", cups);
        factura.append("mes", nombre_mes);
        factura.append("periodo", periodo);
        factura.append("precio_total", precio_total);
        factura.append("precio_total_impuestos", Math.round(precio_total_impuestos * 100) / 100d);
        factura.append("consumo_total", consumo_total);
        factura.append("precios_dia", precios_dia);
        factura.append("consumos_dia", consumos_dia);

        Facturas.add(factura);
    }

    public static void borrar_consumos_contrato(int cups) {

        try {
            BasicDBObject query = new BasicDBObject();
            query.put("cups", cups);

            Document contrato = contratos_collection.find(query).first();

            Document cliente = (Document) contrato.get("cliente");

            List<Document> lista_consumos = (List<Document>) contrato.get("consumo_mes");
            System.out.println("Borra el contrato: " + contrato);
            contratos_collection.deleteOne(contrato);

            for (Document consumo : lista_consumos) {
                System.out.println("Su consumo: " + consumo);
                consumos_collection.deleteOne(consumo);
            }

            BasicDBObject queryCliente = new BasicDBObject();
            queryCliente.put("cliente.DNI", cliente.get("DNI"));
            String DNICliente = cliente.get("DNI").toString();
            FindIterable<Document> contratosCliente = contratos_collection.find(queryCliente);

            int count = 0;
            for (Document contratoCliente : contratosCliente) {
                System.out.println(contratoCliente);
                count++;
            }
            if (count == 0) {
                borrar_contratos_consumos_cliente(DNICliente);
            }

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("No existe ese contrato");
        }

    }

    public static void borrar_contratos_consumos_cliente(String cliente) {

        try {
            BasicDBObject query = new BasicDBObject();
            query.put("cliente.DNI", cliente);

            FindIterable<Document> lista_contratos = contratos_collection.find(query);

            for (Document contrato : lista_contratos) {
                //System.out.println("Borra del cliente: " + cliente + "\nEl contrato: " + contrato);

                List<Document> lista_consumos = (List<Document>) contrato.get("consumo_mes");

                contratos_collection.deleteOne(contrato);

                for (Document consumo : lista_consumos) {
                    System.out.println("Su consumo: " + consumo);
                    consumos_collection.deleteOne(consumo);
                }
            }

            BasicDBObject DNIQuery = new BasicDBObject();
            DNIQuery.put("DNI", cliente);
            cliente_collection.findOneAndDelete(DNIQuery);
        } catch (Exception e) {
            System.out.println("Ese cliente no existe");
        }

    }

    public ArrayList<Document> obtener_contatos(int skip) {
        ArrayList<Document> contratos = new ArrayList();

        FindIterable<Document> iterable_contratos = contratos_collection.find().limit(cantidad).skip(skip);
        for (Document document : iterable_contratos) {
            contratos.add(document);
        }

        //mongo_client.close();
        return contratos;
    }

}
