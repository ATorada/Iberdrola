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

    //Variables
    public static int numeroRegistrosXRound;
    static int contador;
    static int contadorLocal = 0;

    //Crea un índice único en una colección en una field
    public void createUniqueIndex(MongoCollection<Document> collection, String field) {
        Document index = new Document(field, 1);
        collection.createIndex(index, new IndexOptions().unique(true));
    }

    public App() {
        //Cargo las conexiones
        Generador.cargar_conexiones();
        //Genero los Unique Index
        createUniqueIndex(Generador.cliente_collection, "DNI");
        createUniqueIndex(Generador.contratos_collection, "cups");
        //Preparo las facturas
        Generador.preparar_facturas();
        //Genero las tasas del año
        Generador.Generar_Tasas_Año();
        //Establezco el contado en cantidad a los clientes existentes
        contador = (int) Generador.cliente_collection.count();
    }

    public static void main(String[] args) {

        //Llamo a la interfaz
        PrimerInsertUI piui = new PrimerInsertUI(null, false);
        numeroRegistrosXRound = 500000;
        piui.setVisible(true);

        //Tests
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
     * Método que inserta N cantidad de Registros
     *
     * @param cantidadRegistros
     */
    public static void Primer_Genera_Inserta_N(int cantidadRegistros) {

        //Hace un generador que genera X cantidad de registros y base a un contador
        Generador generador = new Generador(cantidadRegistros, contador);
        //Los genera y guarda
        ArrayList<ArrayList<Document>> primerinsert = generador.generar_primerinsert();
        ArrayList<Document> clientes = primerinsert.get(0);
        //ArrayList<Document> consumos = primerinsert.get(1);
        ArrayList<Document> contratos = primerinsert.get(1); //2
        int contadorClientes = 0;
        int contadorContratos = 0;
        //Los inserta
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

        //En caso de que no se haya podido añadir un DNI/Contrato porque fue borrado, he ido aumentando una variable y se crean tantos inserts como esa variable
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
     * Método que se encarga de insertar las facturas de un mes
     *
     * @param mes
     */
    public static void insertar_Facturas(int mes) {

        int NumeroAGenerar = (int) Generador.cliente_collection.count();

        int CantidadADividir;

        //Establece la cantidad para generar el número de iteraciones
        if (NumeroAGenerar >= numeroRegistrosXRound) {
            CantidadADividir = numeroRegistrosXRound;
        } else {
            CantidadADividir = NumeroAGenerar;
        }
        //Conecto con la collección facturas @Deprecates
        MongoCollection<Document> facturas_collection = Generador.mongo_database.getCollection("facturas");
        //Switch que conecta consumos_collection al mes del que queramos generar las facturas en base al mes introducido
        //Y las vacía
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

        //Se obtiene el número de iteraciones
        int NumeroFases = NumeroAGenerar / CantidadADividir;

        int contadorLocal = 0;
        //Si el módulo es cero, se insertarán las facturas del mes
        if (NumeroAGenerar % NumeroFases == 0) {
            for (int i = 0; i < NumeroFases; i++) {
                for (int j = contadorLocal; j < contadorLocal + CantidadADividir; j++) {
                    Generador.Mostrar_Factura_Periodo(j, mes, false);
                }
                contadorLocal += CantidadADividir;
            }
            try {
                facturas_collection.insertMany(Generador.Facturas);
                Generador.Facturas = new ArrayList();
            } catch (Exception e) {
                System.out.println("No hay facturas para generar.");
            }

            //Si el módulo no es cero se insertarán las facturas de un mes y las no añadidas en la cantidad de iteraciones
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
            try {
                facturas_collection.insertMany(Generador.Facturas);
                Generador.Facturas = new ArrayList();
            } catch (Exception e) {
                System.out.println("No hay facturas para generar.");
            }

        }
    }

    /**
     * Método que inserta N registros
     *
     * @param NumeroAGenerar
     */
    public static void insertar_N(int NumeroAGenerar) {

        //Obtiene el número a generar y establece la cantidad a dividir para generar las iteraciones
        //De normal numeroRegistrosXRound son 500000
        int CantidadADividir;

        if (NumeroAGenerar >= numeroRegistrosXRound) {
            CantidadADividir = numeroRegistrosXRound;
        } else {
            CantidadADividir = NumeroAGenerar;
        }

        //Obtiene el número de iteraciones
        int NumeroFases = NumeroAGenerar / CantidadADividir;

        //Si el módulo es cero inserta la cantidad
        if (NumeroAGenerar % CantidadADividir == 0) {
            for (int i = 0; i < NumeroFases; i++) {
                Primer_Genera_Inserta_N(CantidadADividir);
            }
            //Sino es cero inserta la cantidad y la restante
        } else {
            for (int i = 0; i < NumeroFases; i++) {
                Primer_Genera_Inserta_N(CantidadADividir);
            }
            Primer_Genera_Inserta_N(NumeroAGenerar % CantidadADividir);
        }
    }

    /**
     * Método que inserta uun día a todos los registros en base a un mes
     * introducido
     *
     * @param mes
     */
    public static void insertarUnDia(String mes) {

        //Conecta con el mes
        Generador.consumos_collection = Generador.mongo_database.getCollection(mes);

        //Si no hay cero consumos en ese mes, se añaden a los consumos existentes
        if (Generador.consumos_collection.count() != 0) {
            //Establece la cantidad a dividir para generar iteraciones
            contadorLocal = 0;
            int CantidadADividir;
            if (Generador.consumos_collection.count() >= numeroRegistrosXRound) {
                CantidadADividir = numeroRegistrosXRound;
            } else {
                CantidadADividir = (int) Generador.consumos_collection.count();
            }
            int NumeroFases = (int) (Generador.consumos_collection.count() / CantidadADividir);
            //Si el módulo es 0, genera los update
            if (Generador.consumos_collection.count() % CantidadADividir == 0) {
                for (int i = 0; i < NumeroFases; i++) {
                    Genera_Inserta_Dia_Consumo(CantidadADividir, mes);
                }
                //Si el módulo no es 0, genera los update y los restantes
            } else {
                for (int i = 0; i < NumeroFases; i++) {
                    Genera_Inserta_Dia_Consumo(CantidadADividir, mes);
                }
                Genera_Inserta_Dia_Consumo((int) (Generador.consumos_collection.count() % CantidadADividir), mes);
            }
            //Si hay 0 consumos inserta un consumo en cada documento
        } else {
            contadorLocal = 0;
            //Establece la cantidad a Dividir para generar las iteraciones
            int CantidadADividir;
            if (Generador.cliente_collection.count() >= numeroRegistrosXRound) {
                CantidadADividir = numeroRegistrosXRound;
            } else {
                CantidadADividir = (int) Generador.cliente_collection.count();
            }
            int NumeroFases = (int) (Generador.cliente_collection.count() / CantidadADividir);
            //Si el módulo es 0, entonces inserta el nuevo consumo
            if (Generador.cliente_collection.count() % CantidadADividir == 0) {
                for (int i = 0; i < NumeroFases; i++) {
                    Insertar_Mes_Nuevo(CantidadADividir, mes);
                }
                //Si no es 0, inserta el nuevo consumo y los restantes
            } else {
                for (int i = 0; i < NumeroFases; i++) {
                    Insertar_Mes_Nuevo(CantidadADividir, mes);
                }
                Insertar_Mes_Nuevo((int) (Generador.cliente_collection.count() % CantidadADividir), mes);
            }
        }

    }

    /**
     * Método que inserta un mes nuevo en base a una cantidad de registros y en
     * base a un mes
     *
     * @param cantidadRegistros
     * @param mes
     */
    public static void Insertar_Mes_Nuevo(int cantidadRegistros, String mes) {

        //int contadorLocal = 0;
        //Se crea el generador
        Generador generador = new Generador(cantidadRegistros, contador);
        //Se establece la colección
        Generador.consumos_collection = Generador.mongo_database.getCollection(mes);
        //Se generan los consumos y se obtienen los contratos
        ArrayList<Document> consumos = generador.generar_consumos();
        ArrayList<Document> contratos = generador.obtener_contatos(contadorLocal);
        contadorLocal += numeroRegistrosXRound;
        Generador.consumos_collection.insertMany(consumos);

        int contadorLocal = 0;
        //A los documentos obtenidos se les añade el consumo
        for (Document contrato : contratos) {
            Document listItem = new Document("consumo_mes", new BasicDBObject("_id", new ObjectId(consumos.get(contadorLocal).get("_id").toString())));
            Document updateQuery = new Document("$push", listItem);
            Generador.contratos_collection.updateOne(contrato, updateQuery);
            contadorLocal++;
        }

    }

    /**
     * Método que genera e inserta un día de consumo en base a una cantidad de
     * registros y un mes
     *
     * @param cantidadRegistros
     * @param mes
     */
    public static void Genera_Inserta_Dia_Consumo(int cantidadRegistros, String mes) {

        //Instancia un generador
        Generador generador = new Generador(cantidadRegistros, contadorLocal);
        //Establece las cnatidades
        Generador.cantidad = cantidadRegistros;
        Generador.consumos_collection = Generador.mongo_database.getCollection(mes);
        //Obtiene los updates y los documentos
        ArrayList<ArrayList<Document>> updatesDiarios = generador.generar_updatesDiarios(contadorLocal);
        ArrayList<Document> documentos = updatesDiarios.get(0);
        ArrayList<Document> updates = updatesDiarios.get(1);
        contadorLocal += numeroRegistrosXRound;
        int updateContador = 0;
        //Los actualiza
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

    //Método que limpia la Base de Datos
    public void limpiarBBDD() {
        Generador.mongo_database.drop();
    }

}
