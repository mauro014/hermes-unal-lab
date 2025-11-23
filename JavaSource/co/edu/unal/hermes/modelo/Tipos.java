package co.edu.unal.hermes.modelo;

public class Tipos {
	
	private Long id;
	private String nombre;
	private Tipos padre;
	private Long orden;
	
	public static final long Ninguno = 0L;
	public static final long SINO = 1;
	public static final long SI = 2;
	public static final long NO = 3;

	public static final long TIPOMANTENIMIENTOESPECIALIZADO = 4;
	public static final long PREVENTIVO = 5;	

	public static final long ESTADOS_EQUIPO = 19;
	public static final long ESTADOS_EQUIPO_OPTIMO = 20;

	public static final long TIPOS_DOCUMENTACION = 16;
	public static final long TIPOS_EQUIPOS = 12;
	public static final long FRECUENCIA_MANTENIMIENTO = 25;
	public static final Long FRECUENCIA_MANTENIMIENTO_NO_APLICA = 26L;
	public static final long FRECUENCIA_MANTENIMIENTO_SEMESTRAL = 28;
	public static final Long FRECUENCIA_MANTENIMIENTO_TRIANUAL = 157L;

	public static final long TIPO_MANTENIMIENTO = 29;
	public static final long INTERNO = 30;

	public static final long MONEDAS = 7;
	public static final long MONEDAS_PESO = 8;

	public static final long GENERO = 33;

	public static final Long TIPOS_SI_NO = 1L;
	public static final Long TIPO_SI = 2L;
	public static final Long TIPO_NO = 3L;

	public static final Long TIPOS_LABORATORIO = 35L;
	public static final Long TIPO_LABORATORIO_PLANTA = 36L;
	public static final Long TIPO_LABORATORIO_TALLER = 37L;
	public static final Long TIPO_LABORATORIO_AULA_DE_MUSICA = 38L;
	public static final Long TIPO_LABORATORIO_LABORATORIO = 39L;
	public static final Long TIPO_LABORATORIO_CLINICA = 40L;
	public static final Long TIPO_LABORATORIO_BODEGA_ALMACEN = 41L;
	public static final Long TIPO_LABORATORIO_AREA_APOYO = 42L;
	public static final Long TIPO_LABORATORIO_SALA_INFORMATICA = 1135L;

	public static final Long TIPOS_ESTADOS = 43L;
	public static final Long TIPO_ESTADO_BUENO = 44L;
	public static final Long TIPO_ESTADO_REGULAR = 45L;
	public static final Long TIPO_ESTADO_MALO = 46L;
	public static final String NOMBRE_TIPO_ESTADO_BUENO = "Bueno";
	public static final String NOMBRE_TIPO_ESTADO_REGULAR = "Regular";
	public static final String NOMBRE_TIPO_ESTADO_MALO = "Malo";

	public static final Long TIPOS_GESTION_LABORATORIO = 48L;
	public static final Long TIPO_GESTION_LABORATORIO_NO_APLICA = 49L;
	public static final Long TIPO_GESTION_LABORATORIO_SI = 50L;
	public static final Long TIPO_GESTION_LABORATORIO_NO = 51L;
	public static final Long TIPO_GESTION_LABORATORIO_EN_PROCESO = 52L;

	public static final Long TIPOS_PORTAFOLIO = 53L;
	public static final Long TIPOS_PORTAFOLIO_NINGUNO = 57L;

	public static final Long UNIDADES_TIEMPO = 59L;
	public static final Long UNIDAD_TIEMPO_SEGUNDO = 60L;
	public static final Long UNIDAD_TIEMPO_MINUTOS = 61L;
	public static final Long UNIDAD_TIEMPO_HORAS = 62L;
	public static final Long UNIDAD_TIEMPO_DIAS = 63L;
	public static final Long UNIDAD_TIEMPO_SEMANAS = 64L;
	public static final Long UNIDAD_TIEMPO_MESES = 65L;
	public static final Long UNIDAD_TIEMPO_AÑOS = 66L;
	public static final Long UNIDAD_TIEMPO_VARIABLE = 67L;
	public static final Long UNIDAD_TIEMPO_NO_APLICA = 68L;

	public static final Long TIPOS_MECANISMOS_ADQUISICION_EQUIPOS = 70L;

	public static final Long TIPOS_ARCHIVOS_LABORATORIOS = 80L;
	public static final Long TIPO_ARCHIVO_LABORATORIOS_FOTOGRAFIA = 81L;
	public static final Long TIPO_ARCHIVO_LABORATORIOS_MANUAL = 82L;
	public static final Long TIPO_ARCHIVO_LABORATORIOS_PLAN_MANTTO = 88L;
	public static final Long TIPO_ARCHIVO_LAB_EQUIPOS_DADO_BAJA = 7722L;
	
	public static final Long TIPO_ARCHIVO_LABORATORIOS_Proceso_de_Compra = 7262L;
	public static final Long TIPO_ARCHIVO_LABORATORIOS_Manual_de_Operación = 7263L;
	public static final Long TIPO_ARCHIVO_LABORATORIOS_manual_de_instalación = 7264L;
	public static final Long TIPO_ARCHIVO_LABORATORIOS_Lista_Asistencia_a_Entrenamiento = 7265L;
	
	
	public static final Long TIPOS_ARCHIVOS_SOLICITUD_LABORATORIOS = 7259L;
	public static final Long TIPOS_ARCHIVOS_SOLICITUD_LABORATORIOS_Soporte= 7260L;
	
	public static final Long TIPOS_ARCHIVOS_SOLICITUD_LABORATORIOS_Soporte_desvincular_equipo= 7728L;
	public static final Long TIPOS_ARCHIVOS_SOLICITUD_LABORATORIOS_Soporte_desvincular_equipo_sede= 7729L;
	
	public static final Long TIPOS_ARCHIVOS_PERSONA_LABORATORIO = 372L;
	
	public static final Long TIPOS_ARCHIVOS_ACTIVIDAD_LABORATORIO_MANTTO = 7257L;
	public static final Long TIPOS_ARCHIVOS_ACTIVIDAD_LABORATORIO_Informe_Seguimiento = 7258L;
	public static final Long TIPOS_ARCHIVOS_ACTIVIDAD_LABORATORIO_proc_mantenimiento = 7269L;
	
	public static final Long TIPOS_ARCHIVOS_ACTIVIDAD_LABORATORIO_CALIB = 7276L;
	public static final Long TIPOS_ARCHIVOS_ACTIVIDAD_LABORATORIO_proc_confirmacion_metrologica = 7277L;
	public static final Long TIPOS_ARCHIVOS_ACTIVIDAD_LABORATORIO_informe_calibracion = 7278L;
	public static final Long TIPOS_ARCHIVOS_ACTIVIDAD_LABORATORIO_resultados_confirmacion_metrologica = 7279L;
	
	public static final Long TIPOS_ARCHIVOS_EQUIPO_REPORTE_DANIO = 7270L;
	public static final Long TIPOS_ARCHIVOS_EQUIPO_REPORTE_DANIO_Reporte_danio = 7271L;
	
	public static final Long TIPOS_ARCHIVOS_INSUMO_LABORATORIO = 7282L;
	public static final Long TIPOS_ARCHIVOS_INSUMO_LABORATORIO_Ficha_Datos_Seguridad = 7283L;
	public static final Long TIPOS_ARCHIVOS_INSUMO_LABORATORIO_Etiqueta = 7284L;
	
	public static final Long TIPOS_LAB_EQUIPOS_ACTUALIZACIONES_SOFTWARE = 7272L;
	public static final Long TIPOS_LAB_EQUIPOS_ACTUALIZACIONES_SOFTWARE_Software = 7273L;
	public static final Long TIPOS_LAB_EQUIPOS_ACTUALIZACIONES_SOFTWARE_Firmware = 7274L;

	public static final Long TIPOS_ADQUISICIONES_LABORATORIOS = 90L;

	public static final Long TIPOS_FRECUENCIAS_EVALUACION = 100L;

	public static final Long TIPOS_EJECUCION_SOFTWARE = 110L;

	public static final Long TIPOS_FRECUENCIA_MANTENIMIENTO_PREVENTIVO = 120L;
	public static final Long TIPO_FRECUENCIA_MANTENIMIENTO_PREVENTIVO_DIARIO = 122L;
	public static final Long TIPO_FRECUENCIA_MANTENIMIENTO_PREVENTIVO_ANUAL = 135L;

	public static final Long TIPOS_ACTIVIDADES_EQUIPOS_LABORATORIO = 140L;
	public static final Long TIPO_ACTIVIDAD_EQUIPO_CALIBRACION = 142L;
	public static final Long TIPO_ACTIVIDAD_EQUIPO_LABORATORIO_MANTTO_CORR = 144L;
	public static final Long TIPO_ACTIVIDAD_EQUIPO_LABORATORIO_MANTTO_PREV = 145L;
	public static final Long TIPO_ACTIVIDAD_EQUIPO_LABORATORIO_VERIFICACION = 146L;
	public static final Long TIPO_ACTIVIDAD_EQUIPO_LABORATORIO_REVISION_GARANTIA = 148L;
	public static final Long TIPO_ACTIVIDAD_EQUIPO_LAB_DANNIO = 150L;
	public static final Long TIPO_ACTIVIDAD_EQUIPO_LAB_MAL_FUNCIONAMIENTO = 152L;

	public static final Long UNIDADES_MEDIDA_INSUMOS_LABORATORIO = 160L;

	public static final Long TIPOS_SOLICITUDES_LABORATORIO = 180L;
	public static final Long TIPO_SOLICITUD_LABORATORIO_CREACION = 181L;

	public static final Long TIPOS_MAGNITUDES_FISICAS = 190L;
	public static final Long TIPO_MAGNITUD_FISICA_OTRA = 239L;

	public static final Long TIPOS_SOLICITUD_SNL = 240L;
	public static final Long TIPOS_DOCUMENTOS_GESTION_LABORATORIOS = 280L;

	public static final Long TIPOS_DOCUMENTOS_INF_GRAL_LABORATORIOS = 300L;
	public static final Long TIPO_DOCUMENTO_INF_GRAL_ACTO_CREACION = 305L;
	public static final Long TIPO_DOCUMENTO_INF_GRAL_REGLAMENTO = 7665L;
	
	public static final Long TIPOS_DOCUMENTOS_EQUIPOS_LABORATORIO = 7225L;
	public static final Long TIPOS_DOCUMENTOS_EQUIPOS_LABORATORIO_PLAN_MANTTO_GRAL = 7226L;

	public static final Long TIPOS_VINCULACION_PERSONAS_LABORATORIOS = 310L;
	public static final Long TIPO_VICULACION_PERSONA_LABORATORIO_CONTRATISTA = 312L;
	public static final Long TIPO_VICULACION_PERSONA_LABORATORIO_ESTUDIANTE = 314L;
	public static final Long TIPO_VICULACION_PERSONA_LABORATORIO_PLANTA = 316L;
	
	/*Propiedad intelectual*/
	public static final Long GENEROS_MUSICALES = 321L; //Usado para registro de obras musicales
	public static final Long GENEROS_CINEMATOGRAFICOS = 360L; //Usado para registro de obras audiovisuales
	public static final Long CLASIFICACION_OBRAS_AUDIOVISUALES = 403L; //Usado para registro de obras audiovisuales
	public static final Long ESTADO_DESARROLLO_PROPIEDAD_IND = 636L; //Usado para registro de propiedad industrial
	public static final Long SECTOR_TECNOLOGICO_INVENCION = 642L; //Usado para registro de propiedad industrial
	public static final Long TIPO_SOLICITUD_PROP_INT = 1136L; //Usado para registro de propiedad industrial 
	public static final Long ESTADO_DLLO_PROP_INDUSTRIAL_NUEVO = 1140L; //Usado para registro de propiedad industrial
	
	/*avales - convocatoria externa*/
	public static final Long NATURALEZA_CONVOCATORIA_EXTERNA = 375L;
	
	public static final Long TIPOS_FRECUENCIA_MANTENIMIENTO = 25L;
	public static final Long TIPOS_FRECUENCIA_MANTENIMIENTO_NO_APLICA = 26L;
	public static final Long TIPOS_FRECUENCIA_MANTENIMIENTO_MENSUAL = 27L;
	public static final Long TIPOS_FRECUENCIA_MANTENIMIENTO_BIMESTRAL = 380L;
	public static final Long TIPOS_FRECUENCIA_MANTENIMIENTO_TRIMESTRAL = 381L;
	public static final Long TIPOS_FRECUENCIA_MANTENIMIENTO_TRIANUAL = 157L;
	public static final Long TIPOS_FRECUENCIA_MANTENIMIENTO_SEMESTRAL = 28L;
	public static final Long TIPOS_FRECUENCIA_MANTENIMIENTO_ANUAL = 58L;
	public static final Long TIPOS_FRECUENCIA_MANTENIMIENTO_BIENAL = 382L;
	public static final Long TIPOS_FRECUENCIA_MANTENIMIENTO_TRIENAL = 383L;
	public static final Long TIPOS_FRECUENCIA_MANTENIMIENTO_CUATRIENAL = 384L;
	public static final Long TIPOS_FRECUENCIA_MANTENIMIENTO_QUINQUENAL = 385L;
	
	public static final Long TIPOS_OPERACION_LOG_LAB = 386L;
	public static final Long TIPOS_OPERACION_LOG_LAB_CREACION = 387L;
	public static final Long TIPOS_OPERACION_LOG_LAB_ACTUALIZACION = 388L;
	public static final Long TIPOS_OPERACION_LOG_LAB_BORRADO = 389L;
	public static final Long TIPOS_OPERACION_LOG_LAB_CONSULTA = 390L;
	public static final Long TIPOS_OPERACION_LOG_LAB_CAMBIO_NOMBRE = 1122L;
	public static final Long TIPOS_OPERACION_LOG_LAB_CAMBIO_UBICACION = 1123L;
	public static final Long TIPOS_OPERACION_LOG_LAB_CAMBIO_COORDINADOR = 1124L;
	public static final Long TIPOS_OPERACION_LOG_LAB_CAMBIO_TIPO = 7731L;
	public static final Long TIPOS_OPERACION_LOG_LAB_ACTUALIZACION_inf_general = 7704L;
	public static final Long TIPOS_OPERACION_LOG_LAB_ACTUALIZACION_rec_humano = 7705L;
	public static final Long TIPOS_OPERACION_LOG_LAB_ACTUALIZACION_riesgos = 7706L;
	public static final Long TIPOS_OPERACION_LOG_LAB_ACTUALIZACION_gestion = 7707L;
	public static final Long TIPOS_OPERACION_LOG_LAB_ACTUALIZACION_equipos = 7708L;
	public static final Long TIPOS_OPERACION_LOG_LAB_ACTUALIZACION_investigacion = 7709L;
	public static final Long TIPOS_OPERACION_LOG_LAB_ACTUALIZACION_proyectos = 7710L;
	public static final Long TIPOS_OPERACION_LOG_LAB_ACTUALIZACION_docencia = 7711L;
	public static final Long TIPOS_OPERACION_LOG_LAB_ACTUALIZACION_servicios = 7712L;
	public static final Long TIPOS_OPERACION_LOG_LAB_ACTUALIZACION_presupuesto = 7713L;
	public static final Long TIPOS_OPERACION_LOG_LAB_ACTUALIZACION_metrologia = 7714L;
	
	public static final Long TIPO_OPERACION_LOG_EQU = 391L;
	public static final Long TIPO_OPERACION_LOG_EQU_CREACION_DATOS_BASICOS = 392L;
	public static final Long TIPO_OPERACION_LOG_EQU_CREACION_HOJA_DE_VIDA = 393L;
	public static final Long TIPO_OPERACION_LOG_EQU_ACTUALIZACION_DATOS_BASICOS = 394L;
	public static final Long TIPO_OPERACION_LOG_EQU_ACTUALIZACION_HOJA_DE_VIDA = 395L;
	public static final Long TIPO_OPERACION_LOG_EQU_BORRADO_DATOS_BASICOS = 396L;
	public static final Long TIPO_OPERACION_LOG_EQU_BORRADO_HOJA_DE_VIDA = 409L;
	public static final Long TIPO_OPERACION_LOG_EQU_CONSULTA = 397L;
	public static final Long TIPO_OPERACION_LOG_EQU_CALCULO_CRITICIDAD= 7798L;

	public static final Long TIPO_OPERACION_LOG_ACT = 398L;
	public static final Long TIPO_OPERACION_LOG_ACT_CREACION = 399L;
	public static final Long TIPO_OPERACION_LOG_ACT_ACTUALIZACION = 400L;
	public static final Long TIPO_OPERACION_LOG_ACT_BORRADO = 401L;
	public static final Long TIPO_OPERACION_LOG_ACT_CONSULTA = 402L;
	
	/*TIPOS METROLOGIA*/
	public static final Long TIPO_LAB_METRO_SUBRED = 410L;
	public static final Long TIPO_LAB_METRO_SUBRED_Academia = 411L;
	public static final Long TIPO_LAB_METRO_SUBRED_Agropecuaria = 412L;
	public static final Long TIPO_LAB_METRO_SUBRED_Alimentos = 413L;
	public static final Long TIPO_LAB_METRO_SUBRED_Ambiental = 414L;
	public static final Long TIPO_LAB_METRO_SUBRED_Energia_electrica = 415L;
	public static final Long TIPO_LAB_METRO_SUBRED_Farmaceutica_y_cosmeticos = 416L;
	public static final Long TIPO_LAB_METRO_SUBRED_Forense = 417L;
	public static final Long TIPO_LAB_METRO_SUBRED_Industria = 418L;
	public static final Long TIPO_LAB_METRO_SUBRED_Mineria = 419L;
	public static final Long TIPO_LAB_METRO_SUBRED_Salud = 420L;

	public static final Long TIPO_LAB_METRO_CAPACITACION = 421L;
	public static final Long TIPO_LAB_METRO_CAPACITACION_Analisis_de_no_conformidades_en_auditorias_de_la_ISO_17025 = 422L;
	public static final Long TIPO_LAB_METRO_CAPACITACION_Calculo_de_la_incertidumbre_en_mediciones_fisicas = 423L;
	public static final Long TIPO_LAB_METRO_CAPACITACION_Calculo_de_la_incertidumbre_en_mediciones_quimicas = 424L;
	public static final Long TIPO_LAB_METRO_CAPACITACION_Calculo_de_la_incertidumbre_en_microbiologia = 425L;
	public static final Long TIPO_LAB_METRO_CAPACITACION_Estadistica_avanzada_aplicada_a_los_analisis_de_laboratorio = 426L;
	public static final Long TIPO_LAB_METRO_CAPACITACION_Estadistica_avanzada_en_analisis_de_laboratorio = 427L;
	public static final Long TIPO_LAB_METRO_CAPACITACION_Estudios_de_impacto_MQ__PTB = 428L;
	public static final Long TIPO_LAB_METRO_CAPACITACION_Otro = 429L;
	public static final Long TIPO_LAB_METRO_CAPACITACION_Produccion_de_materiales_de_referencia = 430L;
	public static final Long TIPO_LAB_METRO_CAPACITACION_Programas_de_comparacion_interlaboratorio_PCIs_yo_Ensayos_de_Aptitud_EA = 431L;
	public static final Long TIPO_LAB_METRO_CAPACITACION_Tablas_de_control_en_laboratorios = 432L;
	public static final Long TIPO_LAB_METRO_CAPACITACION_Trazabilidad_metrologica = 433L;
	public static final Long TIPO_LAB_METRO_CAPACITACION_Validacion_de_metodos_fisicos = 434L;
	public static final Long TIPO_LAB_METRO_CAPACITACION_Validacion_de_metodos_microbiologicos = 435L;
	public static final Long TIPO_LAB_METRO_CAPACITACION_Validacion_de_metodos_quimicos = 436L;
	public static final Long TIPO_LAB_METRO_CAPACITACION_Vocabulario_internacional_de_metrologia_VIM = 437L;

	public static final Long TIPO_LAB_METRO_ACTIVIDADES = 438L;
	public static final Long TIPO_LAB_METRO_ACTIVIDADES_Control_de_procesos_de_manufactura = 439L;
	public static final Long TIPO_LAB_METRO_ACTIVIDADES_Estudios_e_investigaciones = 440L;
	public static final Long TIPO_LAB_METRO_ACTIVIDADES_Inspeccion_y_control_de_calidad_de_materias_primas_yo_productos = 441L;
	public static final Long TIPO_LAB_METRO_ACTIVIDADES_Realizacion_de_calibraciones = 442L;
	public static final Long TIPO_LAB_METRO_ACTIVIDADES_Realizacion_de_ensayos = 443L;
	public static final Long TIPO_LAB_METRO_ACTIVIDADES_Requerimientos_de_conformidad_con_normas_reglamentos_y_certificados = 444L;

	public static final Long TIPO_LAB_METRO_MUESTRA = 445L;
	public static final Long TIPO_LAB_METRO_MUESTRA_Falta_de_personal = 446L;
	public static final Long TIPO_LAB_METRO_MUESTRA_Recursos_economicos = 447L;
	public static final Long TIPO_LAB_METRO_MUESTRA_Tecnicas = 448L;
	public static final Long TIPO_LAB_METRO_MUESTRA_Tiempo = 449L;
	public static final Long TIPO_LAB_METRO_MUESTRA_Otro = 450L;
	public static final Long TIPO_LAB_METRO_MUESTRA_Contrata_el_servicio_de_muestreo_para_ensayos = 451L;
	public static final Long TIPO_LAB_METRO_MUESTRA_Presta_servicio_de_muestreo_para_realizar_los_ensayos = 452L;

	public static final Long TIPO_LAB_METRO_SISTEMAS_GESTION = 453L;
	public static final Long TIPO_LAB_METRO_SISTEMAS_GESTION_BPL_Buenas_practicas_de_laboratorios = 454L;
	public static final Long TIPO_LAB_METRO_SISTEMAS_GESTION_BPM_Buenas_practicas_de_Manufactura = 455L;
	public static final Long TIPO_LAB_METRO_SISTEMAS_GESTION_HACCP = 456L;
	public static final Long TIPO_LAB_METRO_SISTEMAS_GESTION_ISO__IEC_17025 = 457L;
	public static final Long TIPO_LAB_METRO_SISTEMAS_GESTION_ISO_9001 = 458L;
	public static final Long TIPO_LAB_METRO_SISTEMAS_GESTION_NTC_GP_1000 = 459L;
	public static final Long TIPO_LAB_METRO_SISTEMAS_GESTION_NTC_ISO_14001 = 460L;
	public static final Long TIPO_LAB_METRO_SISTEMAS_GESTION_NTC_ISO_22000 = 461L;
	public static final Long TIPO_LAB_METRO_SISTEMAS_GESTION_NTC_OHAS_18001 = 462L;
	public static final Long TIPO_LAB_METRO_SISTEMAS_GESTION_NTCISO_IE_17043 = 463L;
	public static final Long TIPO_LAB_METRO_SISTEMAS_GESTION_Otro = 464L;
	public static final Long TIPO_LAB_METRO_SISTEMAS_GESTION_SUA_Sistema_unico_de_acreditacion_en_salud = 465L;
	public static final Long TIPO_LAB_METRO_SISTEMAS_GESTION_Utiliza_graficos_de_control = 466L;

	public static final Long TIPO_LAB_METRO_NIVEL_FORMACION = 467L;
	public static final Long TIPO_LAB_METRO_NIVEL_FORMACION_Doctorado = 468L;
	public static final Long TIPO_LAB_METRO_NIVEL_FORMACION_Especializacion = 469L;
	public static final Long TIPO_LAB_METRO_NIVEL_FORMACION_Maestria = 470L;
	public static final Long TIPO_LAB_METRO_NIVEL_FORMACION_Operativo = 471L;
	public static final Long TIPO_LAB_METRO_NIVEL_FORMACION_Otro = 472L;
	public static final Long TIPO_LAB_METRO_NIVEL_FORMACION_Tecnico = 473L;
	public static final Long TIPO_LAB_METRO_NIVEL_FORMACION_Tecnologo = 474L;
	public static final Long TIPO_LAB_METRO_NIVEL_FORMACION_Universitario = 475L;

	public static final Long TIPO_LAB_METRO_SECTOR = 476L;
	public static final Long TIPO_LAB_METRO_SECTOR_Agroquimicos = 477L;
	public static final Long TIPO_LAB_METRO_SECTOR_Alimentos_y_bebidas = 478L;
	public static final Long TIPO_LAB_METRO_SECTOR_Ceramicos__Mineria__Metales_aleaciones = 479L;
	public static final Long TIPO_LAB_METRO_SECTOR_Cinico_y_forense = 480L;
	public static final Long TIPO_LAB_METRO_SECTOR_Farmaceutico_y_ciencia_de_la_vida = 481L;
	public static final Long TIPO_LAB_METRO_SECTOR_Medio_ambiente = 482L;
	public static final Long TIPO_LAB_METRO_SECTOR_Petroquimico__productos_quimicos = 483L;
	public static final Long TIPO_LAB_METRO_SECTOR_Agricola = 484L;
	public static final Long TIPO_LAB_METRO_SECTOR_Alimentos = 485L;
	public static final Long TIPO_LAB_METRO_SECTOR_Ambiental = 486L;
	public static final Long TIPO_LAB_METRO_SECTOR_Cinico = 487L;
	public static final Long TIPO_LAB_METRO_SECTOR_Industrial = 488L;

	public static final Long TIPO_LAB_METRO_MATRIZ = 489L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Abonos = 490L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Fertilizantes = 491L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Bebidas_alcoholicas = 492L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Carnes_y_derivados = 493L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Grasas_y_aceites = 494L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Lacteos = 495L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Otros_alimentos_huevos_miel_refrescos = 496L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Vegetales_y_cereales = 497L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Cementos = 498L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Ceramica_y_vidrio = 499L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Metales_y_aleaciones = 500L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Roca_y_Minerales = 501L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Cabello = 502L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Orina_y_fluidos_renales = 503L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Sangre_suero_y_plasma = 504L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Tejidos_blancos_y_oseos = 505L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Cosmeticos_y_aseo_personal = 506L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Medicamentos = 507L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Productos_veterinarios = 508L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Agua_no_potable = 835L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Agua_potable = 836L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Agua_residual = 837L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Aire_Emisiones = 838L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Suelos_y_sedimentos = 839L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Aromas_y_fragancias = 840L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Biocombustibles = 841L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Carbon = 842L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Caucho = 843L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Crudos_o_petroleo = 844L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Curtido_y_preparado_de_cueros = 845L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Derivados_del_petroleo = 846L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Detergentes_y_jabones = 847L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Gases_combustibles = 848L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Gases_industriales = 849L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Gases_medicinales = 850L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Gasolina = 851L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Papel = 852L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Pigmentos = 853L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Pinturas = 854L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Plasticos_y_polimeros = 855L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Sustancias_y_productos_quimicos = 856L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Textiles = 857L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Abonos_2 = 858L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Suelos_lodos_y_sedimentos = 859L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Alimentos_En_General = 860L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Alimentos_para_consumo_humano_y_animal = 861L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Bebidas_No_alcoholicas_bebidas_gaseosas_o_carbonatadas = 862L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Carnes_y_derivados_carnicos_huevos_pollo = 863L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Agua_envasada_y_agua_de_piscina = 864L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Aguas_En_General = 865L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Aguas_crudassuperficiales_y_subterraneas_pozo = 866L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Diagnostico = 867L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Forense = 868L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Medicamentos_y_Cosmeticos = 869L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Muestreos_de_Superficies_ambientes_y_frotis_de_manos = 870L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Cosmeticos = 871L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Desinfectantes = 872L;
	public static final Long TIPO_LAB_METRO_MATRIZ_Jabones = 873L;



	public static final Long TIPO_LAB_METRO_MENSURANDO = 509L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Acidez = 510L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Acidos_grados_actividad_de_agua = 511L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Alcoholes = 512L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Aminoacidos = 513L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Antibioticos = 514L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Carbohidratos = 515L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Cenizas = 516L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Conductividad_electrolitica = 517L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Contenido_de_azufre = 518L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Contenido_de_yodo = 519L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Digestibilidad_en_pepsina = 520L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Dioxido_de_carbono = 521L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Enzimas = 522L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Fibra = 523L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Fosforo_disponible = 524L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Grasa = 525L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Hidrocarburos_aromaticos = 526L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Humedad = 527L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Indice_octano = 874L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Ion_selectivo = 875L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Medicamentos = 876L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Metabolitos = 877L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Metalespesados_minerales = 878L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Nitrogeno = 879L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Organicos_volatiles = 880L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Otros = 881L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Ozono = 882L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_PH = 883L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Plaguicidas = 884L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Principio_activo = 885L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Solidos_solubles = 886L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Solidos_suspendidos = 887L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Solventes_residuales = 888L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Trigliceridos = 889L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Vitaminas = 890L;
	public static final Long TIPO_LAB_METRO_MENSURANDO_Volatiles = 891L;


	public static final Long TIPO_LAB_METRO_TECNICA = 528L;
	public static final Long TIPO_LAB_METRO_TECNICA_Absorcion_atomica_de_llama = 529L;
	public static final Long TIPO_LAB_METRO_TECNICA_Absorcion_atomica_generador_de_hidruros = 530L;
	public static final Long TIPO_LAB_METRO_TECNICA_Absorcion_atomica_horno_de_grafito_analisis_elemental = 531L;
	public static final Long TIPO_LAB_METRO_TECNICA_Conductividad_electrolitica = 532L;
	public static final Long TIPO_LAB_METRO_TECNICA_Coulombimetria = 533L;
	public static final Long TIPO_LAB_METRO_TECNICA_Cromatografia_de_capa_fina = 534L;
	public static final Long TIPO_LAB_METRO_TECNICA_Cromatografia_de_gases_detectores_convencionales_CG = 535L;
	public static final Long TIPO_LAB_METRO_TECNICA_Cromatografia_liquida_de_alta_eficiencia_acoplado_a_Espectrometria_de_masas = 536L;
	public static final Long TIPO_LAB_METRO_TECNICA_Cromatografia_liquida_de_alta_eficiencia_convencionales_HPLC = 537L;
	public static final Long TIPO_LAB_METRO_TECNICA_Cualitativo = 538L;
	public static final Long TIPO_LAB_METRO_TECNICA_Electrogravimetria = 539L;
	public static final Long TIPO_LAB_METRO_TECNICA_ELISA = 540L;
	public static final Long TIPO_LAB_METRO_TECNICA_Espectrofotometria_IR = 541L;
	public static final Long TIPO_LAB_METRO_TECNICA_Espectrofotometria_RAMAN = 542L;
	public static final Long TIPO_LAB_METRO_TECNICA_Espectrofotometria_Rayos_X = 543L;
	public static final Long TIPO_LAB_METRO_TECNICA_Espectrofotometria_UVVis = 544L;
	public static final Long TIPO_LAB_METRO_TECNICA_Fluorescencia = 545L;
	public static final Long TIPO_LAB_METRO_TECNICA_Galvanometria = 546L;
	public static final Long TIPO_LAB_METRO_TECNICA_Gravimetria = 547L;
	public static final Long TIPO_LAB_METRO_TECNICA_ICP = 892L;
	public static final Long TIPO_LAB_METRO_TECNICA_ICPMS = 893L;
	public static final Long TIPO_LAB_METRO_TECNICA_Otros = 894L;
	public static final Long TIPO_LAB_METRO_TECNICA_Potenciometria = 895L;
	public static final Long TIPO_LAB_METRO_TECNICA_Refractometria = 896L;
	public static final Long TIPO_LAB_METRO_TECNICA_Voltametria = 897L;
	public static final Long TIPO_LAB_METRO_TECNICA_Volumetria = 898L;
	public static final Long TIPO_LAB_METRO_TECNICA_Numero_mas_probable = 899L;
	public static final Long TIPO_LAB_METRO_TECNICA_Presenciaausencia = 900L;
	public static final Long TIPO_LAB_METRO_TECNICA_Recuento_en_placa = 901L;
	public static final Long TIPO_LAB_METRO_TECNICA_UFC_Unidades_Formadoras_de_Colonia = 902L;
	public static final Long TIPO_LAB_METRO_TECNICA_Recuento_en_placa_productos_con_Aw_inferior_o_igual_a_095 = 903L;
	public static final Long TIPO_LAB_METRO_TECNICA_Recuento_en_placa_productos_con_Awsuperior_a_095 = 904L;
	public static final Long TIPO_LAB_METRO_TECNICA_Recuento_en_tubo = 905L;
	public static final Long TIPO_LAB_METRO_TECNICA_Test_de_Mackenzei = 906L;
	public static final Long TIPO_LAB_METRO_TECNICA_Cromatografia_Liquida_de_alta_eficiencia_acoplado_a_espectrometria_de_masas = 907L;
	public static final Long TIPO_LAB_METRO_TECNICA_Filtracion_por_membrana = 908L;
	public static final Long TIPO_LAB_METRO_TECNICA_Recuento_en_camara_Newbauer = 909L;


	public static final Long TIPO_LAB_METRO_CONCENTRACION = 548L;
	public static final Long TIPO_LAB_METRO_CONCENTRACION_1_a_1000_mgkg_ppm = 549L;
	public static final Long TIPO_LAB_METRO_CONCENTRACION_1_a_1000_mgkg_ppb = 550L;
	public static final Long TIPO_LAB_METRO_CONCENTRACION_Inferior_a_1_mgkg_ppb = 551L;
	public static final Long TIPO_LAB_METRO_CONCENTRACION_Mayor_a_1000_mgkg_ppm = 552L;
	public static final Long TIPO_LAB_METRO_CONCENTRACION_Otros = 553L;

	public static final Long TIPO_LAB_METRO_METODO_VALIDADO = 554L;
	public static final Long TIPO_LAB_METRO_METODO_VALIDADO_Efecto_matricial = 555L;
	public static final Long TIPO_LAB_METRO_METODO_VALIDADO_Especificidad = 556L;
	public static final Long TIPO_LAB_METRO_METODO_VALIDADO_Estimacion_de_incertidumbre = 557L;
	public static final Long TIPO_LAB_METRO_METODO_VALIDADO_Exactitud = 558L;
	public static final Long TIPO_LAB_METRO_METODO_VALIDADO_Limite_de_cuantificacion = 559L;
	public static final Long TIPO_LAB_METRO_METODO_VALIDADO_Limite_de_deteccion = 560L;
	public static final Long TIPO_LAB_METRO_METODO_VALIDADO_Linealidad_ = 561L;
	public static final Long TIPO_LAB_METRO_METODO_VALIDADO_Precision_ = 562L;
	public static final Long TIPO_LAB_METRO_METODO_VALIDADO_Robustez = 563L;
	public static final Long TIPO_LAB_METRO_METODO_VALIDADO_Selectividad = 564L;

	public static final Long TIPO_LAB_METRO_TIPO_ENSAYO = 565L;
	public static final Long TIPO_LAB_METRO_TIPO_ENSAYO_Cuantitativo = 566L;
	public static final Long TIPO_LAB_METRO_TIPO_ENSAYO_Cualitativo = 567L;

	public static final Long TIPO_LAB_METRO_MATERIAL_REF_CEPA_CERTIFICADA = 568L;
	public static final Long TIPO_LAB_METRO_MATERIAL_REF_CEPA_CERTIFICADA_Cepa_de_referencia = 569L;
	public static final Long TIPO_LAB_METRO_MATERIAL_REF_CEPA_CERTIFICADA_Cepa_de_reserva = 570L;
	public static final Long TIPO_LAB_METRO_MATERIAL_REF_CEPA_CERTIFICADA_Cepa_de_trabajo = 571L;

	public static final Long TIPO_LAB_METRO_VALIDACION = 572L;
	public static final Long TIPO_LAB_METRO_VALIDACION_Desviacion_negativa = 573L;
	public static final Long TIPO_LAB_METRO_VALIDACION_Desviacion_positiva = 574L;
	public static final Long TIPO_LAB_METRO_VALIDACION_Especificidad = 575L;
	public static final Long TIPO_LAB_METRO_VALIDACION_Exactitud_relativa = 576L;
	public static final Long TIPO_LAB_METRO_VALIDACION_Limite_de_cuantificacion = 577L;
	public static final Long TIPO_LAB_METRO_VALIDACION_Repetibilidad = 578L;
	public static final Long TIPO_LAB_METRO_VALIDACION_Reproductibilidad = 579L;
	public static final Long TIPO_LAB_METRO_VALIDACION_Sensibilidad = 580L;
	public static final Long TIPO_LAB_METRO_VALIDACION_Efecto_Matricial = 581L;
	public static final Long TIPO_LAB_METRO_VALIDACION_Exactitud = 582L;
	public static final Long TIPO_LAB_METRO_VALIDACION_Precision = 583L;
	public static final Long TIPO_LAB_METRO_VALIDACION_Linealidad = 584L;
	public static final Long TIPO_LAB_METRO_VALIDACION_Limite_de_deteccion = 585L;
	public static final Long TIPO_LAB_METRO_VALIDACION_Estimacion_de_Incertidumbre = 586L;
	public static final Long TIPO_LAB_METRO_VALIDACION_Robustez = 587L;
	public static final Long TIPO_LAB_METRO_VALIDACION_Selectividad = 588L;

	public static final Long TIPO_LAB_METRO_CALIDAD_MEDIOS_CULTIVO = 589L;
	public static final Long TIPO_LAB_METRO_CALIDAD_MEDIOS_CULTIVO_Control_de_esterilidad_y_pH = 590L;
	public static final Long TIPO_LAB_METRO_CALIDAD_MEDIOS_CULTIVO_Establecer_tiempo_de_caducidad_adecuado_de_acuerdo_al_tipo_de_Medio_de_cultivo_y_al_analisis = 591L;
	public static final Long TIPO_LAB_METRO_CALIDAD_MEDIOS_CULTIVO_Posee_condiciones_de_almacenamiento_adecuado_Proteccion_contra_agentes_Externos = 592L;
	public static final Long TIPO_LAB_METRO_CALIDAD_MEDIOS_CULTIVO_Realiza_procesos_de_conservacionliofilizacion_ultracongelacion_conservacion_en_nitrogeno_liquido = 593L;
	public static final Long TIPO_LAB_METRO_CALIDAD_MEDIOS_CULTIVO_Selectividad_y_productividad_Metodo_Ecometrico = 594L;

	public static final Long TIPO_LAB_METRO_CONDICIONES_AMBIENTALES = 595L;
	public static final Long TIPO_LAB_METRO_CONDICIONES_AMBIENTALES_Certificacion_de_ciclo_de_esterilizacion = 596L;
	public static final Long TIPO_LAB_METRO_CONDICIONES_AMBIENTALES_Control_ambiental_microbiologico_de_superficies_de_mesones_de_trabajo_y_equipos = 597L;
	public static final Long TIPO_LAB_METRO_CONDICIONES_AMBIENTALES_Control_de_esterilidad_de_material_de_laboratorio = 598L;
	public static final Long TIPO_LAB_METRO_CONDICIONES_AMBIENTALES_Control_de_Humedad = 599L;
	public static final Long TIPO_LAB_METRO_CONDICIONES_AMBIENTALES_Control_de_potencia_germicida_luz_UV = 600L;
	public static final Long TIPO_LAB_METRO_CONDICIONES_AMBIENTALES_Control_de_Temperatura = 601L;

	public static final Long TIPO_LAB_METRO_MAGNITUD_CAMPO_APLICACION = 602L;
	public static final Long TIPO_LAB_METRO_MAGNITUD_CAMPO_APLICACION_Aceleracion_angular = 603L;
	public static final Long TIPO_LAB_METRO_MAGNITUD_CAMPO_APLICACION_Aceleracion_lineal = 604L;

	public static final Long TIPO_LAB_METRO_DESEMPEÑO_LAB_PRUEBA = 605L;
	public static final Long TIPO_LAB_METRO_DESEMPEÑO_LAB_PRUEBA_Satisfactorio = 606L;
	public static final Long TIPO_LAB_METRO_DESEMPEÑO_LAB_PRUEBA_Cuestionable = 607L;
	public static final Long TIPO_LAB_METRO_DESEMPEÑO_LAB_PRUEBA_NoSatisfactorio = 608L;

	public static final Long TIPO_LAB_METRO_PROVEEDOR = 609L;
	public static final Long TIPO_LAB_METRO_PROVEEDOR_Nacional_ = 610L;
	public static final Long TIPO_LAB_METRO_PROVEEDOR_Internacional = 611L;

	public static final Long TIPO_LAB_METRO_TIPO_GRUPO = 612L;
	public static final Long TIPO_LAB_METRO_TIPO_GRUPO_Grupos_Nacionales = 613L;
	public static final Long TIPO_LAB_METRO_TIPO_GRUPO_Grupos_Internacionales = 614L;
	public static final Long TIPO_LAB_METRO_TIPO_GRUPO_Red_tecnologica_ = 615L;

	public static final Long TIPO_LAB_METRO_PERIODO_PARTICIPACION_GRUPO = 616L;
	public static final Long TIPO_LAB_METRO_PERIODO_PARTICIPACION_GRUPO_De_1_a_3_años = 617L;
	public static final Long TIPO_LAB_METRO_PERIODO_PARTICIPACION_GRUPO_De_3_a_5_años = 618L;
	public static final Long TIPO_LAB_METRO_PERIODO_PARTICIPACION_GRUPO_Mas_de_5_años = 619L;
	public static final Long TIPO_LAB_METRO_PERIODO_PARTICIPACION_GRUPO_Menos_de_1_año = 620L;

	public static final Long TIPO_LAB_METRO_NECESITA_USA_MATERIAL_REF = 621L;
	public static final Long TIPO_LAB_METRO_NECESITA_USA_MATERIAL_REF_Necesario = 622L;
	public static final Long TIPO_LAB_METRO_NECESITA_USA_MATERIAL_REF_Utilizado = 623L;

	public static final Long TIPO_LAB_METRO_TIPO_MATERIAL_REF = 624L;
	public static final Long TIPO_LAB_METRO_TIPO_MATERIAL_REF_Material_de_Referencia_certificado_MCR_Internacional = 625L;
	public static final Long TIPO_LAB_METRO_TIPO_MATERIAL_REF_Material_de_Referencia_certificado_MCR_nacional = 626L;
	public static final Long TIPO_LAB_METRO_TIPO_MATERIAL_REF_Material_de_Referencia_MR = 627L;
	public static final Long TIPO_LAB_METRO_TIPO_MATERIAL_REF_Reactivo_de_Grado_Industrial = 628L;
	public static final Long TIPO_LAB_METRO_TIPO_MATERIAL_REF_Sustancia_de_alta_pureza = 629L;
	
	// Enero 2018
	public static final Long TIPO_LAB_METRO_MAGNITUD_FISICA = 657L;
	public static final Long TIPO_LAB_METRO_MAGNITUD_FISICA_Aceleracion = 658L;
	public static final Long TIPO_LAB_METRO_MAGNITUD_FISICA_Acustica_y_Vibraciones = 659L;
	public static final Long TIPO_LAB_METRO_MAGNITUD_FISICA_Color = 660L;
	public static final Long TIPO_LAB_METRO_MAGNITUD_FISICA_Densidad = 661L;
	public static final Long TIPO_LAB_METRO_MAGNITUD_FISICA_Dimensional = 662L;
	public static final Long TIPO_LAB_METRO_MAGNITUD_FISICA_Flujo = 663L;
	public static final Long TIPO_LAB_METRO_MAGNITUD_FISICA_Fotometria = 664L;
	public static final Long TIPO_LAB_METRO_MAGNITUD_FISICA_Fuerza = 665L;
	public static final Long TIPO_LAB_METRO_MAGNITUD_FISICA_Humedad = 666L;
	public static final Long TIPO_LAB_METRO_MAGNITUD_FISICA_Magnetismo = 667L;
	public static final Long TIPO_LAB_METRO_MAGNITUD_FISICA_Magnitudes_Electricas = 668L;
	public static final Long TIPO_LAB_METRO_MAGNITUD_FISICA_Masa = 669L;
	public static final Long TIPO_LAB_METRO_MAGNITUD_FISICA_Optica_y_Radiometria = 670L;
	public static final Long TIPO_LAB_METRO_MAGNITUD_FISICA_Par_Torsional = 671L;
	public static final Long TIPO_LAB_METRO_MAGNITUD_FISICA_Potencia_y_Energia_Electrica = 672L;
	public static final Long TIPO_LAB_METRO_MAGNITUD_FISICA_Presion = 673L;
	public static final Long TIPO_LAB_METRO_MAGNITUD_FISICA_Temperatura = 674L;
	public static final Long TIPO_LAB_METRO_MAGNITUD_FISICA_Tiempo = 675L;
	public static final Long TIPO_LAB_METRO_MAGNITUD_FISICA_Transformadores_de_Medida = 676L;
	public static final Long TIPO_LAB_METRO_MAGNITUD_FISICA_Vacio = 677L;
	public static final Long TIPO_LAB_METRO_MAGNITUD_FISICA_Velocidad = 678L;
	public static final Long TIPO_LAB_METRO_MAGNITUD_FISICA_Viscosidad = 679L;
	public static final Long TIPO_LAB_METRO_MAGNITUD_FISICA_Volumen = 680L;


	public static final Long TIPO_Aceleracion_Aceleracion_Angular = 681L;
	public static final Long TIPO_Aceleracion_Aceleracion_Lineal = 682L;
	public static final Long TIPO_Acustica_y_Vibraciones_Calibracion_de_acelerometro = 683L;
	public static final Long TIPO_Acustica_y_Vibraciones_Calibracion_de_microfonos = 684L;
	public static final Long TIPO_Acustica_y_Vibraciones_Calibracion_de_sonometros = 685L;
	public static final Long TIPO_Acustica_y_Vibraciones_Calibracion_de_ruido = 686L;
	public static final Long TIPO_Acustica_y_Vibraciones_Calibracion_de_vibraciones = 687L;
	public static final Long TIPO_Color_CIELAB = 688L;
	public static final Long TIPO_Color_Reflectancia_Espectral = 689L;
	public static final Long TIPO_Densidad_Densidad_Liquidos = 690L;
	public static final Long TIPO_Densidad_Densidad_Solidos = 691L;
	public static final Long TIPO_Dimensional_Angulo = 692L;
	public static final Long TIPO_Dimensional_Area = 693L;
	public static final Long TIPO_Dimensional_Forma = 694L;
	public static final Long TIPO_Dimensional_Longitud = 695L;
	public static final Long TIPO_Flujo_Gas = 696L;
	public static final Long TIPO_Flujo_Liquido = 697L;
	public static final Long TIPO_Fotometria_Flujo_Luminoso = 698L;
	public static final Long TIPO_Fotometria_Iluminancia = 699L;
	public static final Long TIPO_Fotometria_Intensidad_Luminosa = 700L;
	public static final Long TIPO_Fotometria_Luminancia = 701L;
	public static final Long TIPO_Fuerza_Calibracion_de_equipos_de_ensayo = 702L;
	public static final Long TIPO_Fuerza_Calibracion_de_Patrones = 703L;
	public static final Long TIPO_Fuerza_Dureza = 704L;
	public static final Long TIPO_Fuerza_Pendulos_de_Impacto = 705L;
	public static final Long TIPO_Fuerza_Tension_superficial = 706L;
	public static final Long TIPO_Humedad_Humedad_del_aire = 707L;
	public static final Long TIPO_Humedad_Humedad_gases_diferentes_al_aire = 708L;
	public static final Long TIPO_Humedad_Humedad_Solidos = 709L;
	public static final Long TIPO_Magnetismo_Campo_Magnetico = 710L;
	public static final Long TIPO_Magnetismo_Densidad_de_flujo_magnetico = 711L;
	public static final Long TIPO_Magnetismo_Flujo_magnetico = 712L;
	public static final Long TIPO_Magnetismo_Introduccion_Magnetica = 713L;
	public static final Long TIPO_Magnetismo_Intensidad_de_campo_magnetico = 714L;
	public static final Long TIPO_Magnitudes_Electricas_Capacitancia = 715L;
	public static final Long TIPO_Magnitudes_Electricas_Impedancia = 716L;
	public static final Long TIPO_Magnitudes_Electricas_Inductancia = 717L;
	public static final Long TIPO_Magnitudes_Electricas_Intensidad_de_Corriente_Alterna = 718L;
	public static final Long TIPO_Magnitudes_Electricas_Intensidad_de_Corriente_Continua = 719L;
	public static final Long TIPO_Magnitudes_Electricas_Resistencia = 720L;
	public static final Long TIPO_Magnitudes_Electricas_Tension_Alterna = 721L;
	public static final Long TIPO_Magnitudes_Electricas_Tension_Continua = 722L;
	public static final Long TIPO_Masa_Cuerpos_Libres = 723L;
	public static final Long TIPO_Masa_Instrumentos_de_Pesaje = 724L;
	public static final Long TIPO_Masa_Pesas = 725L;
	public static final Long TIPO_Masa_Radiometria = 726L;
	public static final Long TIPO_Optica_y_Radiometria_Fibras_Opticas = 727L;
	public static final Long TIPO_Optica_y_Radiometria_Optoelectronica = 728L;
	public static final Long TIPO_Optica_y_Radiometria_Polarimetria = 729L;
	public static final Long TIPO_Optica_y_Radiometria_Refractometria = 730L;
	public static final Long TIPO_Par_Torsional_Calibracion_de_Probadores_de_Par = 731L;
	public static final Long TIPO_Par_Torsional_Calibracion_de_Torcometros = 732L;
	public static final Long TIPO_Par_Torsional_Calibracion_de_Trasnductores_de_par = 733L;
	public static final Long TIPO_Par_Torsional_Pistolas_de_Apriete_Neumaticas_e_Hidraulicas = 734L;
	public static final Long TIPO_Par_Torsional_Probadores_de_tapas = 735L;
	public static final Long TIPO_Potencia_y_Energia_Electrica_Energia_Electrica = 736L;
	public static final Long TIPO_Potencia_y_Energia_Electrica_Factor_de_Potencia = 737L;
	public static final Long TIPO_Potencia_y_Energia_Electrica_Potencia_Electrica = 738L;
	public static final Long TIPO_Presion_Absoluta_medio_gas = 739L;
	public static final Long TIPO_Presion_Absoluta_medio_liquido = 740L;
	public static final Long TIPO_Presion_Manometrica_medio_gas = 741L;
	public static final Long TIPO_Presion_Manometrica_medio_liquido = 742L;
	public static final Long TIPO_Temperatura_Medios_Isotermos = 743L;
	public static final Long TIPO_Temperatura_Termometro_de_caratula = 744L;
	public static final Long TIPO_Temperatura_Termometro_de_Resistencia_de_platino = 745L;
	public static final Long TIPO_Temperatura_Termometro_digital = 746L;
	public static final Long TIPO_Temperatura_Termometro_de_vidrio = 747L;
	public static final Long TIPO_Temperatura_Termometro_sin_contacto = 748L;
	public static final Long TIPO_Temperatura_Termopares = 749L;
	public static final Long TIPO_Tiempo_Frecuencia = 750L;
	public static final Long TIPO_Tiempo_Intervalo_de_Tiempo = 751L;
	public static final Long TIPO_Transformadores_de_Medida_Transformadores_de_Corriente = 752L;
	public static final Long TIPO_Transformadores_de_Medida_Transformadores_de_Tension = 753L;
	public static final Long TIPO_Vacio_Alto = 1079L;
	public static final Long TIPO_Vacio_Bajo = 1080L;
	public static final Long TIPO_Vacio_Medio = 1081L;
	public static final Long TIPO_Vacio_Ultra_Alto = 1082L;
	public static final Long TIPO_Velocidad_Velocidad_Angular = 1083L;
	public static final Long TIPO_Velocidad_Velocidad_Lineal = 1084L;
	public static final Long TIPO_Viscosidad_Cinematica = 1085L;
	public static final Long TIPO_Viscosidad_Dinamica = 1086L;
	public static final Long TIPO_Volumen_Grandes_Volumenes = 1087L;
	public static final Long TIPO_Volumen_Medianos_Volumenes = 1088L;
	public static final Long TIPO_Volumen_Micro_Volumen = 1089L;
	public static final Long TIPO_Volumen_Pequeños_Volumenes = 1090L;

	public static final Long TIPO_LAB_METRO_PREFIJO = 754L;
	public static final Long TIPO_LAB_METRO_PREFIJO_yotta_Y1024 = 755L;
	public static final Long TIPO_LAB_METRO_PREFIJO_zetta_Z1021 = 756L;
	public static final Long TIPO_LAB_METRO_PREFIJO_exa_E1018 = 757L;
	public static final Long TIPO_LAB_METRO_PREFIJO_peta_P1015 = 758L;
	public static final Long TIPO_LAB_METRO_PREFIJO_tera_T1012 = 759L;
	public static final Long TIPO_LAB_METRO_PREFIJO_giga_G109 = 760L;
	public static final Long TIPO_LAB_METRO_PREFIJO_mega_M106 = 761L;
	public static final Long TIPO_LAB_METRO_PREFIJO_kilo_k103 = 762L;
	public static final Long TIPO_LAB_METRO_PREFIJO_hecto_h102 = 763L;
	public static final Long TIPO_LAB_METRO_PREFIJO_deca_da101 = 764L;
	public static final Long TIPO_LAB_METRO_PREFIJO_deci_d101 = 765L;
	public static final Long TIPO_LAB_METRO_PREFIJO_centi_c102 = 766L;
	public static final Long TIPO_LAB_METRO_PREFIJO_mili_m103 = 767L;
	public static final Long TIPO_LAB_METRO_PREFIJO_micro_µ106 = 768L;
	public static final Long TIPO_LAB_METRO_PREFIJO_nano_n109 = 769L;
	public static final Long TIPO_LAB_METRO_PREFIJO_pico_p1012 = 770L;
	public static final Long TIPO_LAB_METRO_PREFIJO_femto_f1015 = 771L;
	public static final Long TIPO_LAB_METRO_PREFIJO_atto_a1018 = 772L;
	public static final Long TIPO_LAB_METRO_PREFIJO_zeptroz1021 = 773L;
	public static final Long TIPO_LAB_METRO_PREFIJO_yocto_y1024 = 774L;

	public static final Long TIPO_LAB_METRO_UNIDAD = 775L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Metro_por_segundo_cuadrado = 776L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Radianes_por_segundo_cuadrado = 777L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Gramo_por_centimetro_cubico = 778L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Gramo_por_mili_litro = 779L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Kilogramo_por_metro_cubico = 780L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Grados = 781L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Metro = 782L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Metro_cubicos = 783L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Minutos = 784L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Segundos = 785L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Kilogramo_por_hora = 786L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Kilogramos_por_minuto = 787L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Litro_por_minuto = 788L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Metro_cubico_por_hora = 789L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Candela = 790L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Candela_por_metro_cuadrado = 791L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Lumen = 792L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Newton = 793L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Newton_metro = 794L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Newton_por_metro = 796L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Porcentaje_por_humedad_relativa = 797L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Amperio_por_metro = 798L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Gauss = 799L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Maxwell = 800L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Oersted = 801L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Tesla = 802L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Weber = 803L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Amperio = 804L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Culombio = 805L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Faradio = 806L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Henrio = 807L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Ohmio = 808L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Voltio = 809L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Gramo = 810L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Tonelada = 811L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Julio = 812L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Potencia_Activa = 813L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Potencia_Aparente = 814L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Potencia_Reactiva = 815L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Vatio = 816L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Pascal = 817L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Hercio = 818L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Hora = 819L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Minuto = 820L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Segundo = 821L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Metro_por_segundo = 822L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Radianes_por_segundo = 823L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Newtonsegundo_por_metro_cuadrado = 824L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Pascal_por_segundo = 825L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Centimetro_cubico = 826L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Litro = 827L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Metro_Cubico = 828L;
	public static final Long TIPO_LAB_METRO_UNIDAD_No_Aplica = 829L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Lux = 830L;
	public static final Long TIPO_LAB_METRO_UNIDAD_Grados_Celsius = 831L;

	public static final Long TIPO_LAB_METRO_INCERT_MED_INCERTIDUMBRE = 832L;
	public static final Long TIPO_LAB_METRO_INCERT_MED_INCERTIDUMBRE_Absoluta = 833L;
	public static final Long TIPO_LAB_METRO_INCERT_MED_INCERTIDUMBRE_Relativa = 834L;

	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO = 910L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Bacillus_spp = 911L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Baciluus_cereus = 912L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Bacterias_Heterotrofas = 913L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Bacterias_mesodilas = 914L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Bacterias_mesofilas_de_acido_lactico = 915L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Bacterias_sulfato_reductoras = 916L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Bacterias_totales = 917L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Closdridium_perfringens_Esporas_sulfito_reductor = 918L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Closdridium_sulfito_reductor = 919L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Coliformes_fecales = 920L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Coliformes = 921L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Coliformes_totales = 922L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Coliformes_y_E_coli_utilizando_sustrato_definido_enzimatico = 923L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Coliformes_y_E_Coli_azucares = 924L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Concentracion_minima_inhibitoria = 925L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Deteccion_de_E_coli_O157 = 926L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Deteccion_de_Escherichiacoli = 927L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Deteccion_de_Listeria__monocytogenes_en_alimentos_ = 928L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Deteccion_de_Listeria__monocytogenes_en_carnes_y_derivadas_carnicos_huevo_pollo = 929L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Deteccion_de_microorganismos_especificos_y_no_especificos_en_productos_cosmeticos = 930L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Deteccion_de_salmonella = 931L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Deteccion_de_Staphylococcusaureus = 932L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Deteccion_de_Pseudomas_aeruginosa_en_productos_cosmeticos = 933L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Deteccion_de_Staphylococcusaureus_aureus_en_productos_cosmeticos = 934L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Determinacion_de_Legionnella = 935L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Ecoli_Betaglucoronidasa_positiva = 936L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Efectividad_Bactericida_de_desinfectantes = 937L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Enterobacter_sakasakii = 938L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Enterobacterias = 939L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Enumeracion_de_bacterias_aerobias_mesofilas_en_productos_cosmeticos = 940L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Enumeracion_de_mohos_y_levaduras_en_productos_cosmeticos = 941L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Escherichia_coli = 942L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Esporas_de_clostridium_sulfito_reductor_ = 943L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Esterilidad_comercial = 944L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Estreptococos_Fecales_y_Entrecoccus = 945L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Estreptococos_y_Enterococcus = 946L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Evaluacion_de_la_actividad_bactericida_de_antisepticos_y_desinfectantes = 947L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Evaluacion_de_la_actividad_fungicida_y_levaduricida_de_antisepticos_y_desinfectantes = 948L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Hongos_y_Levaduras = 949L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Huevos_de_Helminto = 950L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Listeria_monocytogenes = 951L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Mohos_y_levaduras = 952L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_NMP_de_Coliformes_totales_y_fecales = 953L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Ps_aeruginosa = 954L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Pseudomonas_aeruginosa = 955L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Pseudomonas_spp = 956L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Recuento_de_Bacillus_cereuss = 957L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Recuento_de_bacterias_Heterotrofas = 958L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Recuento_de_coliformes_totales = 959L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Recuento_de_Estafilococo_coagulasa_positiva = 960L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Recuento_de_Microorganismos_Aerobios_Mesofilos = 961L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Recuento_de_Microorganismos = 962L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Recuento_de_Pseudomonas_aeruginosa = 963L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Salmonella_sp = 964L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Staphylococcus_aureus_coagulosa_positiva_y_otras_especies = 965L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Staphylococcus_spp_o_Staphylococcus_ñococcus_aureus = 966L;
	public static final Long TIPO_LAB_METRO_NOMBRE_ENSAYO_Vidrio_Cholerae = 967L;


	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO = 968L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Absorcion_atomica_de_llama = 969L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Absorcion_atomica_generador_de_hidruros = 970L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Absorcion_atomica_horno_de_grafito_analisis_elemental = 971L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Conductividad_electrolitica = 972L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Coulombimetria = 973L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Cromatografia_de_capa_fina = 974L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Cromatografia_de_gases_detectores_convencionales_CG = 975L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Cromatografia_liquida_de_alta_eficiencia_acoplado_a_Espectrometria_de_masas = 976L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Cromatografia_liquida_de_alta_eficiencia_convencionales_HPLC = 977L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Cualitativo = 978L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Electrogravimetria = 979L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_ELISA = 980L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Espectrofotometria_IR = 981L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Espectrofotometria_RAMAN = 982L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Espectrofotometria_Rayos_X = 983L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Espectrofotometria_UVVis = 984L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Fluorescencia = 985L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Galvanometria = 986L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Gravimetria = 987L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_ICP = 988L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_ICPMS = 989L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Otros = 990L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Potenciometria = 991L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Refractometria = 992L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Voltametria = 993L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Volumetria = 994L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Numero_mas_probable = 995L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Presenciaausencia = 996L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Recuento_en_placa = 997L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_UFC_Unidades_Formadoras_de_Colonia = 998L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Recuento_en_placa_productos_con_Aw_inferior_o_igual_a_095 = 999L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Recuento_en_placa_productos_con_Awsuperior_a_095 = 1000L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Recuento_en_tubo = 1001L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Test_de_Mackenzei = 1002L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Cromatografia_Liquida_de_alta_eficiencia_acoplado_a_espectrometria_de_masas = 1003L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Filtracion_por_membrana = 1004L;
	public static final Long TIPO_LAB_METRO_TECNICA_MED_MICRO_Recuento_en_camara_Newbauer = 1005L;

	public static final Long TIPO_LAB_METRO_NORMA_TECNICA = 1006L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_Basado_en_ISO_7932_2004 = 1007L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_NTC_4679_2006 = 1008L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_ASTM_D_4412_2005 = 1009L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_Microbiologia_de_suelos_de_Mayea_Sergio = 1010L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_EPA_1680_2010 = 1011L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_Basado_en_ISO_48322006 = 1012L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_NTC_4458_2007 = 1013L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_Basado_en_Iso_4832_2006 = 1014L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_AOAC_OfficialMethods_96624_Ed_18 = 1015L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_Bacteriological_Analytical_Manual_on_line_April_2003_Chapter_10 = 1016L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_Basado_en_SM_9230B_Ed_21_año_2005_S1_M2_NS7 = 1017L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_ISO_112901_1996 = 1018L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_ISO_112902_1996 = 1019L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_NTX_4666_1999 = 1020L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_FIUC = 1021L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_Tecnicas_en_Microbiologia_de_suelos_y_lodos_Proyecto_CIC = 1022L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_Basado_en_ISO_13720_2000 = 1023L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_Basado_en_ISO_6579_2002 = 1024L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_EPA_1682_2010 = 1025L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_NTC_4574_2007 = 1026L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_Basado_en_ISO_68881_1999 = 1027L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_NTC_4779_2007 = 1028L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_CFSAN_BAM = 1029L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_Listeria_monocytogenes = 1030L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_US_FDA = 1031L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_Colony = 1032L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_Count_technique_at_30C = 1033L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_Horizontal_method_for_the_enumeration_of_presumptuve_Bacillus_cereus = 1034L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_ISO_79322004_E_Microbiology_of_food_and_animal_stuffs = 1035L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_11999_Microbiology_of_food_and_animal_feeding_stuffs = 1036L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_EN_ISO_6888 = 1037L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_Horizontal_method_for_the_enumeration_of_coagulase = 1038L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_Parker_agar_medium = 1039L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_positive_staphylococci__staphylococcus_aureus_and_other_species_Parte_1Techinque_using_Baird = 1040L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_E_Microbiology_of_food_and_animal_feeding_stuffs = 1041L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_Horizontal_method_for_the_enumeration_of_microorganisms = 1042L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_ISO_48332003 = 1043L;
//	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_ISO_48332003 = 1044L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_NF_28_Examen_microbiologico_de_productos_no_esteriles_pruebas_de_microorganismos_especificos_62_p80_y_4236 = 1045L;
//	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_NF_28_Examen_microbiologico_de_productos_no_esteriles_pruebas_de_microorganismos_especificos_62_p80_y_4236 = 1046L;
//	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_NF_28_Examen_microbiologico_de_productos_no_esteriles_pruebas_de_microorganismos_especificos62_p80_y_4236 = 1047L;
//	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_NF_28_Examen_microbiologico_de_productos_no_esteriles_pruebas_de_microorganismos_especificos_62_p80_y_4236 = 1048L;
//	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_NF_28_Examen_microbiologico_de_productos_no_esteriles_pruebas_de_microorganismos_especificos_62_p80_y_4236 = 1049L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_NF_28_Suplementos_Dietarios_62 = 1050L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_USP_33 = 1051L;
//	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_USP_33 = 1052L;
//	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_USP_33 = 1054L;
//	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_USP_33 = 1055L;
//	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_USP_33 = 1056L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_ICMSF_Metodo_1_2001 = 1057L;
//	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_ICMSF_Metodo_1_2001 = 1058L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_NTC_4519_2009 = 1059L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_ISO_15214_1998 = 1060L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_NTC_5034_2002 = 1061L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_ISO_7937_2004 = 1062L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_NTC_4834_2000 = 1063L;
//	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_NTC_4834_2000 = 1064L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_APHA_873_2000 = 1065L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_ISO_4831_2006 = 1066L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_NTC_4516_2009 = 1067L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_INVIMS_1889 = 1068L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_APHA_8933_2000 = 1069L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_ISO_16654_2001 = 1070L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_NTC_4899_2001 = 1071L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_ISO_166493_2005 = 1072L;
//	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_ISO_166493_2005 = 1073L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_ISO_22964_2006 = 1074L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_APHA_862_2000 = 1075L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_ISO_215282_2009 = 1076L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_AOAC_97244_Ed_18_años_2da_revision_año_2007 = 1077L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_NTC_4433_2006 = 1078L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_APHA_95_y_96_2000 = 1098L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_ICMSF_2001 = 1099L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_SM_9215_B_Ed_21_año_2005 = 1100L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_ISO_6461_Parte_1_1986 = 1101L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_NTC_5056_Parte_1_2002 = 1102L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_SM_9221_B_Ed_21_año_2005 = 1103L;
//	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_SM_9221_B_Ed_21_año_2005 = 1104L;
//	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_SM_9221_B_Ed_21_año_2005 = 1105L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_SM_9223_B_Ed_21_año_2005 = 1106L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_SM_9230_B_Ed_21_año_2005 = 1107L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_SM_9230_C_Ed_21_año_2005 = 1108L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_SM_9610_B_Ed_21_año_2005 = 1109L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_SM_9213_E_Ed_21_año_2005 = 1110L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_ISO_18593_2004 = 1111L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_NTC_5230_2003 = 1112L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_ISO_18415_2007 = 1113L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_ISO_22717_2006 = 1114L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_ISO_22718_2006 = 1115L;
	public static final Long TIPO_LAB_METRO_NORMA_TECNICA_ISO_21149_2006 = 1116L;

	public static final Long TIPO_LAB_METRO_DONDE_CALIBRADO = 1092L;
	public static final Long TIPO_LAB_METRO_DONDE_CALIBRADO_Interno = 1093L;
	public static final Long TIPO_LAB_METRO_DONDE_CALIBRADO_Externo = 1094L;

	public static final Long TIPO_LAB_METRO_INCERTIDUMBRE_CALIBRADO = 1095L;
	public static final Long TIPO_LAB_METRO_DONDE_CALIBRADO_Absoluta = 1096L;
	public static final Long TIPO_LAB_METRO_DONDE_CALIBRADO_Relativa = 1097L;


	/*TIPOS METROLOGIA*/
	
	/*categorias de indicadores Indicadores*/
	public static final Long CATEGORIAS_INDICADORES = 630L; //Usado para categorias de indicadores
    public static final Long CAT_CONV_INTERNA = 631L; //categoria de convocatorias internas
    public static final Long CAT_AVALES= 632L; //categoria de avales
    public static final Long CAT_GRUPOS= 633L; //categoria de grupos de investigacion
    public static final Long CAT_INVESTIGADORES = 634L; //categoria de investigadores
    public static final Long CAT_COLECCIONES = 635L; //categoria de colecciones
	/*fin de indicadores Indicadores*/
    
    public static final Long TIPO_LAB_SUSTANCIAS_ACTIVIDAD_MANEJO = 653L;
	public static final Long TIPO_LAB_SUSTANCIAS_ACTIVIDAD_MANEJO_Ingreso = 654L;
	public static final Long TIPO_LAB_SUSTANCIAS_ACTIVIDAD_MANEJO_Consumo = 655L;
	public static final Long TIPO_LAB_SUSTANCIAS_ACTIVIDAD_MANEJO_Almacenamiento = 656L;
	
	public static final Long TIPOS_LAB_SUSTANCIAS_UNIDAD_CONCENTRACION = 7287L;
	public static final Long TIPOS_LAB_SUSTANCIAS_UNIDAD_CONCENTRACION_MM = 7288L;
	public static final Long TIPOS_LAB_SUSTANCIAS_UNIDAD_CONCENTRACION_MV = 7289L;
	public static final Long TIPOS_LAB_SUSTANCIAS_UNIDAD_CONCENTRACION_VV = 7290L;
	public static final Long TIPOS_LAB_SUSTANCIAS_UNIDAD_CONCENTRACION_PPM = 7291L;
	public static final Long TIPOS_LAB_SUSTANCIAS_UNIDAD_CONCENTRACION_MOLAR = 7292L;
	public static final Long TIPOS_LAB_SUSTANCIAS_UNIDAD_CONCENTRACION_NORMAL = 7293L;
	
	public static final Long TIPO_LAB_MODALIDAD_ACTIVIDAD = 1117L;
	public static final Long TIPO_LAB_MODALIDAD_ACTIVIDAD_Interno = 1118L;
	public static final Long TIPO_LAB_MODALIDAD_ACTIVIDAD_Externo = 1119L;
	public static final Long TIPO_LAB_MODALIDAD_ACTIVIDAD_No_Aplica = 1120L;
	public static final Long TIPO_LAB_MODALIDAD_ACTIVIDAD_No_Especificado = 1121L;
	
	public static final Long TIPOS_LAB_NORMA_TECNICA_ENSAYO_SERVICIO = 1125L;
	public static final Long TIPOS_LAB_NORMA_TECNICA_ENSAYO_SERVICIO_NTC = 1126L;
	public static final Long TIPOS_LAB_NORMA_TECNICA_ENSAYO_SERVICIO_ASTM = 1127L;
	public static final Long TIPOS_LAB_NORMA_TECNICA_ENSAYO_SERVICIO_INVIMA = 1128L;
	public static final Long TIPOS_LAB_NORMA_TECNICA_ENSAYO_SERVICIO_ISO = 1129L;
	public static final Long TIPOS_LAB_NORMA_TECNICA_ENSAYO_SERVICIO_IEC = 1130L;
	public static final Long TIPOS_LAB_NORMA_TECNICA_ENSAYO_SERVICIO_ITU = 1131L;
	public static final Long TIPOS_LAB_NORMA_TECNICA_ENSAYO_SERVICIO_AOAC = 7256L;
	
	public static final Long TIPOS_DOCUMENTOS_ENS_SERV_LABORATORIOS = 1132L;
	public static final Long TIPOS_DOCUMENTOS_ENS_SERV_LABORATORIOS_BROCHURE = 1133L;
	public static final Long TIPOS_DOCUMENTOS_ENS_SERV_LABORATORIOS_ACTO_TARIFAS = 1134L;
	public static final Long TIPOS_DOCUMENTOS_ENS_SERV_LABORATORIOS_CONDICIONES = 7654L;
	
	public static final Long TIPOS_LAB_AREAS_CIENTIFICAS_TECNOLOGICAS_OCDE = 1153L;
	public static final Long TIPOS_LAB_OBJETIVOS_SOCIOECONOMICOS = 1202L;
	
//	ISBN
	public static final Long TIPOS_ISBN_CLASIFICACION_THEMA = 1217L;
	public static final Long TIPOS_ISBN_CALIFICADOR_2_LENGUA = 3926L;
	public static final Long TIPOS_ISBN_CALIFICADOR_3_PERIODO_HISTORICO = 4213L;
	public static final Long TIPOS_ISBN_CALIFICADOR_4_FIN_DIDACTICO = 4414L;
	public static final Long TIPOS_ISBN_CALIFICADOR_1_LUGAR = 4699L;
	public static final Long TIPOS_ISBN_CALIFICADOR_5_EDAD_INTERES_INTE_ESPECIALES = 6933L;
	public static final Long TIPOS_ISBN_CALIFICADOR_6_ESTILO = 7034L;
	
	public static final Long TIPOS_EQUIPO_RAZON_NO_QUIPU = 7210L;
	public static final Long TIPOS_EQUIPO_RAZON_NO_QUIPU_OTROS = 7214L;
	
	public static final Long TIPOS_ISBN_DISPONIBILIDAD = 7215L;
	public static final Long TIPOS_ISBN_TIPO_ACCESO = 7221L;
	
	//SOLICITUD LABORATORIOS
	public static final Long TIPOS_TIPO_SOLICITUD_LABORATORIO = 7227L;
	public static final Long TIPOS_TIPO_SOLICITUD_LABORATORIO_eliminar_equipo = 7228L;
	public static final Long TIPOS_TIPO_SOLICITUD_LABORATORIO_Crear_Laboratorio = 7254L;
	public static final Long TIPOS_ESTADO_SOLICITUD_LABORATORIO = 7229L;
	public static final Long TIPOS_ESTADO_SOLICITUD_LABORATORIO_Ingresando = 7255L;
	public static final Long TIPOS_ESTADO_SOLICITUD_LABORATORIO_Enviada = 7230L;
	public static final Long TIPOS_ESTADO_SOLICITUD_LABORATORIO_Aprobada = 7231L;
	public static final Long TIPOS_ESTADO_SOLICITUD_LABORATORIO_Rechazada = 7232L;
	
	//GESTION LABS 2019
	public static final Long TIPOS_LAB_GESTION_REQUIERE = 7234L;
	public static final Long TIPOS_LAB_GESTION_INTERESADO = 7239L;
	
	//ENSAYOS LABS 2019
	public static final Long TIPOS_LAB_TIPO_ENSAYO = 7242L;
	public static final Long TIPOS_LAB_TIPO_ENSAYO_ensayo = 7243L;
	public static final Long TIPOS_LAB_TIPO_ENSAYO_calib = 7244L;
	public static final Long TIPOS_LAB_TIPO_ENSAYO_otros = 7246L;
	public static final Long TIPOS_LAB_ENSAYO_ACREDITADO = 7247L;
	
	public static final Long TIPOS_TIPO_REGLAMENTO_LAB = 7660L;
	
	public static final Long TIPOS_SOL_LAB_MOTIVO = 7724L;
	
	public static final Long TIPOS_LAB_ENSAYOS_MAGNITUD_AREA = 7611L;
	public static final Long TIPOS_LAB_ENSAYOS_ORGANISMO_ACREDITA = 7651L;
	
	public static final Long TIPO_SOLICITUD_PROYECTOS_LABS = 7656L;
	
	public static final Long TIPOS_LAB_DEPTO_CIUDAD = 7599L;
	
	public static final Long TIPOS_OBJETIVOS_DESARROLLO_SOSTENIBLE = 7666L;
	
	public static final Long TIPOS_TIPO_RECURSO_AVAL_ETICO = 7684L;
	public static final Long TIPOS_TIPO_RECURSO_AVAL_ETICO_Reposicion = 7685L;
	public static final Long TIPOS_TIPO_RECURSO_AVAL_ETICO_Reposición_Apelación = 7686L;
	public static final Long TIPOS_TIPO_RECURSO_AVAL_ETICO_Apelacion = 7687L;
	
	public static final Long TIPOS_TIPO_AVAL_ESTADO_ARTICULO = 7688L;
	
	//Tipos persona lab
	public static final Long TIPOS_TIPO_PERSONA_LABORATORIO = 7695L;
	public static final Long TIPOS_TIPO_PERSONA_LABORATORIO_Docente_planta = 7696L;
	public static final Long TIPOS_TIPO_PERSONA_LABORATORIO_Docente_no_planta = 7697L;
	public static final Long TIPOS_TIPO_PERSONA_LABORATORIO_Admin_planta = 7698L;
	public static final Long TIPOS_TIPO_PERSONA_LABORATORIO_Admin_no_planta = 7699L;
	public static final Long TIPOS_TIPO_PERSONA_LABORATORIO_Estudiante = 7700L;
	public static final Long TIPOS_TIPO_PERSONA_LABORATORIO_Egresado = 7701L;
	public static final Long TIPOS_TIPO_PERSONA_LABORATORIO_Contratista = 7702L;
	public static final Long TIPOS_TIPO_PERSONA_LABORATORIO_Externo = 7703L;
	
	//Tipos criticidad equipos
	public static final Long TIPOS_CRITIC_EQUIPOS_IMPACTO_OPER_LAB = 7732L;
	public static final Long TIPOS_CRITIC_EQUIPOS_IMPACTO_SEG_USUARIOS = 7738L;
	public static final Long TIPOS_CRITIC_EQUIPOS_IMPACTO_DANIOS_INFRA = 7744L;
	public static final Long TIPOS_CRITIC_EQUIPOS_IMPACTO_DANIOS_AMBIENT = 7750L;
	public static final Long TIPOS_CRITIC_EQUIPOS_IMPACTO_IMAGEN_UN = 7756L;
	public static final Long TIPOS_CRITIC_EQUIPOS_IMPACTO_QUEJAS = 7762L;
	public static final Long TIPOS_CRITIC_EQUIPOS_IMPACTO_IMPACTOS_ECON = 7768L;
	public static final Long TIPOS_CRITIC_EQUIPOS_PROBAB_REP_FALLA = 7774L;
	public static final Long TIPOS_CRITIC_EQUIPOS_PROBAB_TIEMPO_TRAB = 7780L;
	public static final Long TIPOS_CRITIC_EQUIPOS_PROBAB_COND_AMB = 7786L;
	public static final Long TIPOS_CRITIC_EQUIPOS_PROBAB_METROLOGIA = 7792L;
	
	//Tipos parametrización convocatorias x sede o facultad
	public static final Long TIPOS_PARAM_CONV_MOV = 7803L;
	public static final Long TIPOS_PARAM_CONV_MOV_DOCENTE_CONV_PADRE_SEDE = 7804L;
	public static final Long TIPOS_PARAM_CONV_MOV_DOCENTE_CONV_SEDE = 7805L;
	public static final Long TIPOS_PARAM_CONV_MOV_DOCENTE_CONV_ANIO = 7806L;
	public static final Long TIPOS_PARAM_CONV_MOV_DOCENTE_CONV_PONENCIA = 7807L;
//	public static final Long TIPOS_PARAM_CONV_MOV_DOCENTE_CONV_PONENCIA_MONTO_MAX = 7808L;
	public static final Long TIPOS_PARAM_CONV_MOV_ESTUDIANTE_CONV_ANIO = 7809L;
//	public static final Long TIPOS_PARAM_CONV_MOV_ESTUDIANTE_CONV_NIVEL_PROGRAMA = 7810L;
//	public static final Long TIPOS_PARAM_CONV_MOV_ESTUDIANTE_CONV_PONENCIA_MONTO_MAX = 7811L;
	
	/**
	 * @param id
	 */
	public Tipos(Long id) {
		super();
		this.id = id;
	}

	public Tipos() {
		super();
	}

	@Override
	public boolean equals(Object otroObjeto) {
		boolean igual = false;
		if (otroObjeto != null && otroObjeto instanceof Tipos) {
			Tipos otroDetalle = (Tipos) otroObjeto;

			if (otroDetalle.id == null || this.id == null) {
				igual = false;
			} else {
				igual = this.id.equals(otroDetalle.id);
			}
		}
		return igual;
	}

	/**
	 * @return Returns the id.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return Returns the nombre.
	 */
	public String getNombre() {
		return nombre;
	}

	public Tipos getPadre() {
		return padre;
	}

	public void setPadre(Tipos padre) {
		this.padre = padre;
	}

	/**
	 * @param nombre
	 *            The nombre to set.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setIdString(String id) {
		this.id = new Long(id);
	}

	public String getIdString() {
		return this.id.toString();
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public String getNombreTipo() {

		String nombresTipos[] = { "", "SI/NO", "SI", "NO", "TIPO MANTENIMIENTO ESPECIALIZADO", "PREVENTIVO",
				"CORRECTIVO", "MONEDAS", "PESO", "DOLAR", "YEN", "LIBRA", "TIPOS EQUIPOS", "MANUAL", "ELECTRICO",
				"MECANICO", "TIPOS DOCUMENTACION", "MANUAL DE FUNCIONAMIENTO", "BITACORA", "ESTADOS EQUIPO", "OPTIMO",
				"BUENO", "EN REPARACION", "OBSOLETO", "NO SE SABE USAR", "FRECUENCIA MANTENIMIENTO", "No aplica",
				"Mensual", "Semestral", "TIPO MANTENIMIENTO", "INTERNO", "EXTERNO", "NO SE REALIZA", "BITACORA",
				"CATALOGO DE ESPECIFICACIONES", "TIPOS DE LABORATORIO", "Planta", "Taller", "Aula de Música",
				"Laboratorio de Práctica", "Clínica", "Bodega/Almacén", "Área de Apoyo", "ESTADOS", "Bueno", "Regular",
				"Malo", "No Aplica", "GESTION_LABORATORIO", "No Aplica", "Si", "No", "En Proceso", "TIPOS_PORTAFOLIO",
				"Digital", "Impreso", "Ambos", "Ninguno", "Anual", "UNIDADES TIEMPO", "Segundo(s)", "Minutos(s)",
				"Hora(s)", "Día(s)", "Semana(s)", "Mes(es)", "Año(s)", "Variable", "No Aplica", "Desconocido",
				"MECANISMOS_ADQUISICION_EQUIPOS", "Apoyo a Doctorados", "Desconocido", "Desconocido",
				"Convocatoria Nacional Dotación y Reposición Equipos Laboratorio 2012", "Desconocido", "Desconocido",
				"Proyecto de Inversión", "Desconocido", "Desconocido", "TIPOS_ARCHIVOS_LABORATORIOS", "Fotografía",
				"Manual", "Desconocido", "Desconocido", "Desconocido", "Desconocido", "Desconocido", "Desconocido",
				"Desconocido", "TIPOS_ADQUISICIONES_LABORATORIOS", "Orden de Compra", "Orden de Servicios",
				"Desconocido", "Desconocido", "Desconocido", "Desconocido", "Desconocido", "Desconocido", "Desconocido",
				"TIPOS_FRECUENCIAS_EVALUACION", "Desconocido", "Siempre", "Desconocido", "Desconocido", "A veces",
				"Desconocido", "Desconocido", "Nunca", "Desconocido", "TIPOS_EJECUCION_SOFTWARE", "Cliente/Servidor",
				"Individual" };

		if (id.intValue() >= nombresTipos.length) {
			return ("Tipo Desconocido");
		} else {
			return nombresTipos[id.intValue()];
		}

	}
}
