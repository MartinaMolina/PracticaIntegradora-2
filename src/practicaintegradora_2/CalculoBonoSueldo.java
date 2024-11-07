package practicaintegradora_2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CalculoBonoSueldo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Datos iniciales del empleado
        System.out.println("Ingrese el nombre completo del empleado:");
        String nombreEmpleado = scanner.nextLine();

        System.out.println("Ingrese el CUIL del empleado:");
        long cuil = scanner.nextLong();

        System.out.println("Ingrese el año de ingreso:");
        int anioIngreso = scanner.nextInt();

        System.out.println("Ingrese el sueldo básico:");
        double sueldoBasico = scanner.nextDouble();

        Empleado empleado = new Empleado(nombreEmpleado, cuil, anioIngreso, sueldoBasico);

        // Validar y calcular antigüedad
        int anioActual = 2024; // Suponiendo el año actual
        while (anioIngreso > anioActual) {
            System.out.println("El año de ingreso no puede ser mayor al año actual. Ingrese nuevamente:");
            anioIngreso = scanner.nextInt();
            empleado.setAnioIngreso(anioIngreso);
        }
        empleado.calcularAntiguedad(anioActual);

        // Crear instancia de BonoSueldo
        System.out.println("Ingrese el mes de liquidación (1-12):");
        int mesLiquidacion = scanner.nextInt();

        System.out.println("Ingrese el año de liquidación:");
        int anioLiquidacion = scanner.nextInt();

        while (anioLiquidacion > anioActual) {
            System.out.println("El año de liquidación no puede ser mayor al año actual. Ingrese nuevamente:");
            anioLiquidacion = scanner.nextInt();
        }

        BonoSueldo bono = new BonoSueldo(empleado, mesLiquidacion, anioLiquidacion);

        // Arrays de haberes y deducciones
        String[][] haberes = {
            {"100", "Presentismo", "9"},
            {"101", "Titulo Profesional", "9"},
            {"102", "Horas Extraordinarias", "M"},
            {"103", "Horas Nocturnas", "M"},
            {"104", "Otros Haberes", "M"}
        };

        String[][] deducciones = {
            {"200", "Obra Social", "3"},
            {"201", "Jubilacion", "11"},
            {"202", "Sindicato", "2"},
            {"203", "Seguro", "1.5"},
            {"204", "Otros", "M"}
        };

        // Matriz de bono calculado
        String[][] bonoCalculado = new String[10][4];

        // Lista de códigos ingresados
        List<Integer> codigosIngresados = new ArrayList<>();

        // Paso 3: Solicitar ingreso de haberes
        boolean ingresoHaberValido = false;
        while (!ingresoHaberValido) {
            System.out.println("Ingrese los Haberes del Empleado:");
            while (true) {
                System.out.println("Ingrese el código del ítem (o 000 para terminar):");
                int codigo = scanner.nextInt();

                if (codigo == 000) {
                    if (codigosIngresados.isEmpty()) {
                        System.out.println("Debe ingresar al menos 1 haber.");
                    } else {
                        ingresoHaberValido = true;
                        break;
                    }
                } else if (codigosIngresados.contains(codigo)) {
                    System.out.println("El código ya ha sido cargado, ingrese otro código.");
                } else {
                    // Verificar si el código existe en haberes
                    boolean codigoEncontrado = false;
                    for (String[] item : haberes) {
                        if (Integer.parseInt(item[0]) == codigo) {
                            codigoEncontrado = true;
                            String denominacion = item[1];
                            String tipoCalculo = item[2];

                            // Calcular monto en base al tipo de cálculo
                            double monto = 0;
                            if (tipoCalculo.equals("M")) {
                                System.out.println("Ingrese el monto para " + denominacion + ":");
                                monto = scanner.nextDouble();
                            } else {
                                double porcentaje = Double.parseDouble(tipoCalculo);
                                monto = sueldoBasico * (porcentaje / 100);
                            }

                            // Agregar a bonoCalculado
                            bonoCalculado[codigosIngresados.size()][0] = item[0]; // Código
                            bonoCalculado[codigosIngresados.size()][1] = denominacion; // Denominación
                            bonoCalculado[codigosIngresados.size()][2] = String.valueOf(monto); // Monto

                            codigosIngresados.add(codigo);
                            break;
                        }
                    }

                    if (!codigoEncontrado) {
                        System.out.println("El código ingresado es incorrecto.");
                    }
                }
            }
        }

        // Paso 4: Solicitar ingreso de deducciones
        boolean ingresoDeduccionValido = false;
        while (!ingresoDeduccionValido) {
            System.out.println("Ingrese las Deducciones del Empleado:");
            while (true) {
                System.out.println("Ingrese el código del ítem (o 000 para terminar):");
                int codigo = scanner.nextInt();

                if (codigo == 000) {
                    if (codigosIngresados.isEmpty()) {
                        System.out.println("Debe ingresar al menos 1 deducción.");
                    } else {
                        ingresoDeduccionValido = true;
                        break;
                    }
                } else if (codigosIngresados.contains(codigo)) {
                    System.out.println("El código ya ha sido cargado, ingrese otro código.");
                } else {
                    // Verificar si el código existe en deducciones
                    boolean codigoEncontrado = false;
                    for (String[] item : deducciones) {
                        if (Integer.parseInt(item[0]) == codigo) {
                            codigoEncontrado = true;
                            String denominacion = item[1];
                            String tipoCalculo = item[2];

                            // Calcular monto en base al tipo de cálculo
                            double monto = 0;
                            if (tipoCalculo.equals("M")) {
                                System.out.println("Ingrese el monto para " + denominacion + ":");
                                monto = scanner.nextDouble();
                            } else {
                                double porcentaje = Double.parseDouble(tipoCalculo);
                                monto = sueldoBasico * (porcentaje / 100);
                            }

                            // Agregar a bonoCalculado
                            bonoCalculado[codigosIngresados.size()][0] = item[0]; // Código
                            bonoCalculado[codigosIngresados.size()][1] = denominacion; // Denominación
                            bonoCalculado[codigosIngresados.size()][3] = String.valueOf(monto); // Monto

                            codigosIngresados.add(codigo);
                            break;
                        }
                    }

                    if (!codigoEncontrado) {
                        System.out.println("El código ingresado es incorrecto.");
                    }
                }
            }
        }

        // Calcular el monto total de liquidación
        double totalHaberes = 0;
        double totalDeducciones = 0;
        for (String[] fila : bonoCalculado) {
            if (fila[2] != null) {
                totalHaberes += Double.parseDouble(fila[2]);
            }
            if (fila[3] != null) {
                totalDeducciones += Double.parseDouble(fila[3]);
            }
        }

        double montoLiquidacion = sueldoBasico + empleado.getMontoAntiguedad() + totalHaberes - totalDeducciones;
        bono.setMontoLiquidacion(montoLiquidacion);

        // Mostrar el bono de sueldo a liquidar en el formato solicitado
        System.out.println("\n+---------------------------------------------+");
        System.out.printf("| %-40s |\n", "Nombre: " + nombreEmpleado);
        System.out.printf("| %-40s |\n", "CUIL: " + cuil);
        System.out.println("+---------------------------------------------+");
        System.out.printf("| Mes Liquidación: %-5d | Año Liquidación: %-5d |\n", mesLiquidacion, anioLiquidacion);
        System.out.printf("| Sueldo Básico: %-5.2f | Año Ingreso: %-5d |\n", sueldoBasico, anioIngreso);
        System.out.println("+---------------------------------------------+");
        System.out.println("| Código Ítem   | Denominación             | Haberes | Deducciones |");
        System.out.println("+---------------------------------------------+");
        
        // Imprimir los detalles de haberes y deducciones
        double totalHaberesImpreso = 0;
        double totalDeduccionesImpreso = 0;

        // Sueldo Básico
        System.out.printf("| %-13s | %-24s | %-8.2f | %-12s |\n", "", "Sueldo Básico", sueldoBasico, "");
        totalHaberesImpreso += sueldoBasico;

        // Antigüedad
        System.out.printf("| %-13s | %-24s | %-8.2f | %-12s |\n", "", "Antigüedad", empleado.getMontoAntiguedad(), "");
        totalHaberesImpreso += empleado.getMontoAntiguedad();

        // Haberes
        for (String[] fila : bonoCalculado) {
            if (fila[2] != null) {
                System.out.printf("| %-13s | %-24s | %-8.2f | %-12s |\n", fila[0], fila[1], Double.parseDouble(fila[2]), "");
                totalHaberesImpreso += Double.parseDouble(fila[2]);
            }
            if (fila[3] != null) {
                System.out.printf("| %-13s | %-24s | %-8s | %-12.2f |\n", fila[0], fila[1], "", Double.parseDouble(fila[3]));
                totalDeduccionesImpreso += Double.parseDouble(fila[3]);
            }
        }

        // Mostrar subtotales
        System.out.println("+---------------------------------------------+");
        System.out.printf("| Subtotal Haberes: %-5.2f | Subtotal Deducciones: %-5.2f |\n", totalHaberesImpreso, totalDeduccionesImpreso);
        System.out.printf("| Neto a Liquidar: %-30.2f |\n", montoLiquidacion);
        System.out.println("+---------------------------------------------+");
    }
}
