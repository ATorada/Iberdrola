package com.antogo.MongoDBFinal;

import com.antogo.Generador.Generador;
import com.antogo.Visual.PrimerInsertUI;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * Hello world!
 *
 */
public class App {

    public static int numeroRegistrosXRound;
    static int contador;
    static int contadorLocal = 0;

    public void createUniqueIndex(MongoCollection<Document> collection, String field) {
        Document index = new Document(field, 1);
        collection.createIndex(index, new IndexOptions().unique(true));
    }

    public App() {
        Generador.cargar_conexiones();
        createUniqueIndex(Generador.cliente_collection, "DNI");
        createUniqueIndex(Generador.contratos_collection, "cups");
        Generador.preparar_facturas();
        Generador.Generar_Tasas_Año();
        contador = (int) Generador.cliente_collection.count();
    }

    public static void main(String[] args) {

        PrimerInsertUI piui = new PrimerInsertUI(null, false);
        numeroRegistrosXRound = 500000;
        piui.setVisible(true);

        //insertar_N(1000000);
        //insertar_N(10);
        //for (int i = 0; i < 31; i++) {
        //insertarUnDia("consumo_febrero");
        //}
        //Generador.preparar_facturas();
        //Generador.Generar_Tasas_Año();
        //Generador.EncontrarContratoCliente(new ObjectId("6210054e1c46ae7cca9153ce"));
        //Generador.Devolver_Consumos(new ObjectId("6210054e1c46ae7cca9153e2"));
        //Generador.Devolver_Factura_Periodo(5, 0);
        //insertar_Facturas(10, 1);
        //Generador.borrar_consumos_contrato(0);
        //for(int i = 0; i < contador; i++) {
        //Generador.borrar_contratos_consumos_cliente("DNI"+i);
        //}
        //Generador.mongo_client.close();
    }

    /**
     *
     * @param cantidadRegistros
     */
    public static void Primer_Genera_Inserta_N(int cantidadRegistros) {

        Generador generador = new Generador(cantidadRegistros, contador);
        ArrayList<ArrayList<Document>> primerinsert = generador.generar_primerinsert();
        ArrayList<Document> clientes = primerinsert.get(0);
        //ArrayList<Document> consumos = primerinsert.get(1);
        ArrayList<Document> contratos = primerinsert.get(1); //2
        int contadorClientes = 0;
        int contadorContratos = 0;
        try {
            Generador.cliente_collection.insertMany(clientes);
        } catch (Exception e) {
            contadorClientes++;
        }
        System.out.println("Se han insertado " + cantidadRegistros + " clientes");
        //Generador.consumos_collection.insertMany(consumos);
        System.out.println("Se han insertado " + cantidadRegistros + " consumos");
        try {
            Generador.contratos_collection.insertMany(contratos);
        } catch (Exception e) {
            contadorContratos++;
        }
        System.out.println("Se han insertado " + cantidadRegistros + " contratos");
        contador += cantidadRegistros;

        if (contadorClientes > 0 || contadorContratos > 0) {
            Generador generadorClientes = new Generador(contadorClientes, Integer.parseInt(Generador.cliente_collection.find().sort(new BasicDBObject("_id", -1)).first().get("DNI").toString().split("I")[1]) + 1);
            ArrayList<ArrayList<Document>> insertDocs = generadorClientes.generar_primerinsert();
            ArrayList<Document> insertClientes = insertDocs.get(0);
            Generador.cliente_collection.insertMany(insertClientes);
            //ArrayList<Document> consumos = primerinsert.get(1);
            ArrayList<Document> insertContratos = insertDocs.get(1); //2

            Generador.contratos_collection.insertMany(insertContratos);
            System.out.println("Se han insertado un total de " + (contador + contadorClientes) + " registros");
        } else {
            System.out.println("Se han insertado un total de " + contador + " registros");
        }

    }

    /**
     *
     * @param mes
     */
    public static void insertar_Facturas(int mes) {

        int NumeroAGenerar = (int) Generador.cliente_collection.count();

        int CantidadADividir;

        if (NumeroAGenerar >= numeroRegistrosXRound) {
            CantidadADividir = numeroRegistrosXRound;
        } else {
            CantidadADividir = NumeroAGenerar;
        }
        MongoCollection<Document> facturas_collection = Generador.mongo_database.getCollection("facturas");
        switch (mes) {
            case 0:
                Generador.consumos_collection = Generador.mongo_database.getCollection("consumo_enero");

                try {
                    Generador.mongo_database.getCollection("facturas_enero").drop();
                } catch (Exception e) {
                }

                facturas_collection = Generador.mongo_database.getCollection("facturas_enero");
                break;
            case 1:
                Generador.consumos_collection = Generador.mongo_database.getCollection("consumo_febrero");
                try {
                    Generador.mongo_database.getCollection("facturas_febrero").drop();
                } catch (Exception e) {
                }

                facturas_collection = Generador.mongo_database.getCollection("facturas_febrero");
                break;
            case 2:
                Generador.consumos_collection = Generador.mongo_database.getCollection("consumo_marzo");
                try {
                    Generador.mongo_database.getCollection("facturas_marzo").drop();
                } catch (Exception e) {
                }

                facturas_collection = Generador.mongo_database.getCollection("facturas_marzo");
                break;
            case 3:
                Generador.consumos_collection = Generador.mongo_database.getCollection("consumo_abril");
                try {
                    Generador.mongo_database.getCollection("facturas_abril").drop();
                } catch (Exception e) {
                }

                facturas_collection = Generador.mongo_database.getCollection("facturas_abril");
                break;
            case 4:
                Generador.consumos_collection = Generador.mongo_database.getCollection("consumo_mayo");
                try {
                    Generador.mongo_database.getCollection("facturas_mayo").drop();
                } catch (Exception e) {
                }

                facturas_collection = Generador.mongo_database.getCollection("facturas_mayo");
                break;
            case 5:
                Generador.consumos_collection = Generador.mongo_database.getCollection("consumo_junio");
                try {
                    Generador.mongo_database.getCollection("facturas_junio").drop();
                } catch (Exception e) {
                }

                facturas_collection = Generador.mongo_database.getCollection("facturas_junio");
                break;
            case 6:
                Generador.consumos_collection = Generador.mongo_database.getCollection("consumo_julio");
                try {
                    Generador.mongo_database.getCollection("facturas_julio").drop();
                } catch (Exception e) {
                }

                facturas_collection = Generador.mongo_database.getCollection("facturas_julio");
                break;
            case 7:
                Generador.consumos_collection = Generador.mongo_database.getCollection("consumo_agosto");
                try {
                    Generador.mongo_database.getCollection("facturas_agosto").drop();
                } catch (Exception e) {
                }

                facturas_collection = Generador.mongo_database.getCollection("facturas_agosto");
                break;
            case 8:
                Generador.consumos_collection = Generador.mongo_database.getCollection("consumo_septiembre");
                try {
                    Generador.mongo_database.getCollection("facturas_septiembre").drop();
                } catch (Exception e) {
                }

                facturas_collection = Generador.mongo_database.getCollection("facturas_septiembre");
                break;
            case 9:
                Generador.consumos_collection = Generador.mongo_database.getCollection("consumo_octubre");
                try {
                    Generador.mongo_database.getCollection("facturas_octubre").drop();
                } catch (Exception e) {
                }

                facturas_collection = Generador.mongo_database.getCollection("facturas_octubre");
                break;
            case 10:
                Generador.consumos_collection = Generador.mongo_database.getCollection("consumo_noviembre");
                try {
                    Generador.mongo_database.getCollection("facturas_noviembre").drop();
                } catch (Exception e) {
                }

                facturas_collection = Generador.mongo_database.getCollection("facturas_noviembre");
                break;
            case 11:
                Generador.consumos_collection = Generador.mongo_database.getCollection("consumo_diciembre");
                try {
                    Generador.mongo_database.getCollection("facturas_diciembre").drop();
                } catch (Exception e) {
                }

                facturas_collection = Generador.mongo_database.getCollection("facturas_diciembre");
                break;
        }

        int NumeroFases = NumeroAGenerar / CantidadADividir;

        int contadorLocal = 0;
        if (NumeroAGenerar % NumeroFases == 0) {
            for (int i = 0; i < NumeroFases; i++) {
                for (int j = contadorLocal; j < contadorLocal + CantidadADividir; j++) {
                    Generador.Mostrar_Factura_Periodo(j, mes, false);
                }
                contadorLocal += CantidadADividir;
            }
            facturas_collection.insertMany(Generador.Facturas);
            Generador.Facturas = new ArrayList();
        } else {
            for (int i = 0; i < NumeroFases; i++) {
                for (int j = contadorLocal; j < contadorLocal + CantidadADividir; j++) {
                    System.out.println(j);
                    Generador.Mostrar_Factura_Periodo(j, mes, false);
                }
                contadorLocal += CantidadADividir;
                System.out.println("Se han insertado " + contadorLocal + " facturas");
            }
            for (int j = contadorLocal; j < contadorLocal + NumeroAGenerar % CantidadADividir; j++) {

                Generador.Mostrar_Factura_Periodo(j, mes, false);
            }
            facturas_collection.insertMany(Generador.Facturas);
            Generador.Facturas = new ArrayList();
        }
    }

    /**
     *
     * @param NumeroAGenerar
     */
    public static void insertar_N(int NumeroAGenerar) {
        int CantidadADividir;

        if (NumeroAGenerar >= numeroRegistrosXRound) {
            CantidadADividir = numeroRegistrosXRound;
        } else {
            CantidadADividir = NumeroAGenerar;
        }

        int NumeroFases = NumeroAGenerar / CantidadADividir;

        if (NumeroAGenerar % CantidadADividir == 0) {
            for (int i = 0; i < NumeroFases; i++) {
                Primer_Genera_Inserta_N(CantidadADividir);
            }
        } else {
            for (int i = 0; i < NumeroFases; i++) {
                Primer_Genera_Inserta_N(CantidadADividir);
            }
            Primer_Genera_Inserta_N(NumeroAGenerar % CantidadADividir);
        }
    }

    /**
     *
     * @param mes
     */
    public static void insertarUnDia(String mes) {

        Generador.consumos_collection = Generador.mongo_database.getCollection(mes);

        if (Generador.consumos_collection.count() != 0) {
            contadorLocal = 0;
            int CantidadADividir;
            if (Generador.consumos_collection.count() >= numeroRegistrosXRound) {
                CantidadADividir = numeroRegistrosXRound;
            } else {
                CantidadADividir = (int) Generador.consumos_collection.count();
            }
            int NumeroFases = (int) (Generador.consumos_collection.count() / CantidadADividir);
            if (Generador.consumos_collection.count() % CantidadADividir == 0) {
                for (int i = 0; i < NumeroFases; i++) {
                    Genera_Inserta_Dia_Consumo(CantidadADividir, mes);
                }
            } else {
                for (int i = 0; i < NumeroFases; i++) {
                    Genera_Inserta_Dia_Consumo(CantidadADividir, mes);
                }
                Genera_Inserta_Dia_Consumo((int) (Generador.consumos_collection.count() % CantidadADividir), mes);
            }
        } else {
            contadorLocal = 0;
            int CantidadADividir;
            if (Generador.cliente_collection.count() >= numeroRegistrosXRound) {
                CantidadADividir = numeroRegistrosXRound;
            } else {
                CantidadADividir = (int) Generador.cliente_collection.count();
            }
            int NumeroFases = (int) (Generador.cliente_collection.count() / CantidadADividir);
            if (Generador.cliente_collection.count() % CantidadADividir == 0) {
                for (int i = 0; i < NumeroFases; i++) {
                    Insertar_Mes_Nuevo(CantidadADividir, mes);
                }
            } else {
                for (int i = 0; i < NumeroFases; i++) {
                    Insertar_Mes_Nuevo(CantidadADividir, mes);
                }
                Insertar_Mes_Nuevo((int) (Generador.cliente_collection.count() % CantidadADividir), mes);
            }
        }

    }

    /**
     *
     * @param cantidadRegistros
     * @param mes
     */
    public static void Insertar_Mes_Nuevo(int cantidadRegistros, String mes) {

        //int contadorLocal = 0;
        Generador generador = new Generador(cantidadRegistros, contador);
        Generador.consumos_collection = Generador.mongo_database.getCollection(mes);
        ArrayList<Document> consumos = generador.generar_consumos();
        ArrayList<Document> contratos = generador.obtener_contatos(contadorLocal);
        contadorLocal += numeroRegistrosXRound;
        Generador.consumos_collection.insertMany(consumos);

        int contadorLocal = 0;
        for (Document contrato : contratos) {
            Document listItem = new Document("consumo_mes", new BasicDBObject("_id", new ObjectId(consumos.get(contadorLocal).get("_id").toString())));
            Document updateQuery = new Document("$push", listItem);
            Generador.contratos_collection.updateOne(contrato, updateQuery);
            contadorLocal++;
        }

    }

    /**
     *
     * @param cantidadRegistros
     * @param mes
     */
    public static void Genera_Inserta_Dia_Consumo(int cantidadRegistros, String mes) {

        Generador generador = new Generador(cantidadRegistros, contadorLocal);
        Generador.cantidad = cantidadRegistros;
        Generador.consumos_collection = Generador.mongo_database.getCollection(mes);
        ArrayList<ArrayList<Document>> updatesDiarios = generador.generar_updatesDiarios(contadorLocal);
        ArrayList<Document> documentos = updatesDiarios.get(0);
        ArrayList<Document> updates = updatesDiarios.get(1);
        contadorLocal += numeroRegistrosXRound;
        int updateContador = 0;
        for (Document document : documentos) {
            //System.out.println(document);
            List<Document> cantidadDias = (List<Document>) document.get("consumo_fecha");
            //System.out.println(document);
            if (cantidadDias.size() < 31) {
                Generador.consumos_collection.updateOne(document, updates.get(updateContador));
                updateContador++;
            }
        }

    }

}
