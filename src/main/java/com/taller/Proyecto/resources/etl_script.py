import pandas as pd
from sqlalchemy import create_engine, MetaData, Table, Column, Integer, ForeignKey
from sqlalchemy.sql import text


# Configuración de la conexión a la base de datos original y al data warehouse
engine_login_system = create_engine('mysql+pymysql://root:root@localhost:3306/login_system')
engine_datawarehouse = create_engine('mysql+pymysql://root:root@localhost:3306/datawarehouse')

# Funciones de Extracción para cada tabla
def extract_table_to_df(table_name, engine):
    return pd.read_sql_table(table_name, engine)

def extract_relationships():
    df_campus = extract_table_to_df('campus', engine_login_system)
    df_campus_dea = extract_table_to_df('campus_dea', engine_login_system)
    df_campus_of_obligatory_spaces = extract_table_to_df('campus_of_obligatory_spaces', engine_login_system)
    df_campus_representatives = extract_table_to_df('campus_representatives', engine_login_system)

    # Realiza las fusiones
    df_combined = pd.merge(df_campus, df_campus_dea, how='left', left_on='id', right_on='campus_id')
    df_combined = pd.merge(df_combined, df_campus_of_obligatory_spaces, how='left', left_on='id', right_on='campus_id')
    df_combined = pd.merge(df_combined, df_campus_representatives, how='left', left_on='id', right_on='campus_id')

    # Imprime las columnas para verificar
    print("Columnas en df_combined después de las fusiones:", df_combined.columns.tolist())

  
    df_indices = df_combined.rename(columns={
        'id': 'id_sede',  
        'campus_id': 'id_sede',  
        'user_id': 'id_representante',  

    })

    df_indices = df_indices[['id_sede', 'id_representante']]  
    return df_indices

def transform_sudden_death(df_sudden_death, df_campus, df_dea):
    df_sudden_death = df_sudden_death.merge(df_dea[['id']], left_on='dea_id', right_on='id', how='left')
    df_sudden_death = df_sudden_death.merge(df_campus[['id', 'province']], left_on='campus_id', right_on='id', how='left')
    df_sudden_death_final = df_sudden_death[['name', 'id', 'rcp', 'dea_id', 'campus_id', 'province']]
    df_sudden_death_final.rename(columns={'name': 'nombre', 'province': 'ubicacion'}, inplace=True)
    
    return df_sudden_death_final



def transform_fechas(df_sudden_death):
    df_sudden_death['created_date'] = pd.to_datetime(df_sudden_death['created_date'])
    df_fecha = pd.DataFrame()
    df_fecha['fecha_id'] = range(1, len(df_sudden_death) + 1) 
    df_fecha['muerte_subita_id'] = df_sudden_death['id'] 
    df_fecha['ano'] = df_sudden_death['created_date'].dt.year
    df_fecha['trimestre'] = df_sudden_death['created_date'].dt.quarter
    df_fecha['mes'] = df_sudden_death['created_date'].dt.month
    df_fecha['dia'] = df_sudden_death['created_date'].dt.day
    
    return df_fecha
def transform_dea_muerte_subita(df_sudden_death, df_dea):
    df_sudden_death['dea_id'] = df_sudden_death['dea_id'].astype('Int64') 

    df_dea_muerte_subita = pd.merge(
        df_sudden_death[['id', 'dea_id']],
        df_dea[['id', 'brand', 'model']],
        left_on='dea_id',
        right_on='id',
        how='left'
    )

    df_dea_muerte_subita_final = df_dea_muerte_subita[['id_x', 'brand', 'model']]
    df_dea_muerte_subita_final.rename(columns={
        'id_x': 'muerte_subita_id',
        'brand': 'marca',
        'model': 'modelo'
    }, inplace=True)

    df_dea_muerte_subita_final.insert(0, 'id', range(1, len(df_dea_muerte_subita_final) + 1))
    
    return df_dea_muerte_subita_final

# Funciones de Transformación para cada tipo de datos
def transform_sedes(df_campus, df_campus_dea, df_status_description):

    dea_count = df_campus_dea.groupby('campus_id').size().reset_index(name='cantidad_deas')
    df_campus = df_campus.merge(dea_count, how='left', left_on='id', right_on='campus_id')
    df_campus['cantidad_deas'] = df_campus['cantidad_deas'].fillna(0)

    df_campus = df_campus.merge(df_status_description, how='left', left_on='status', right_on='status')
    df_sedes = df_campus.rename(columns={
        'id_x': 'sede_id',
        'name_y': 'estado_de_sede',  
        'average_visits': 'visitas',
        'area': 'superficie'
    })

    df_sedes = df_sedes[['sede_id', 'estado_de_sede', 'visitas', 'superficie', 'cantidad_deas']]
    return df_sedes



def transform_representantes(df_users, df_campus_representatives):
    df_merged = pd.merge(df_users, df_campus_representatives, left_on='id', right_on='user_id', how='inner')
    df_representantes = df_merged[['id_x', 'date_of_birth', 'campus_id']].copy()
    df_representantes.rename(columns={'id_x': 'representante_id', 'campus_id': 'sede_id'}, inplace=True)
    df_representantes['Anio_nacimiento'] = pd.DatetimeIndex(df_representantes['date_of_birth']).year
    df_representantes['Mes_nacimiento'] = pd.DatetimeIndex(df_representantes['date_of_birth']).month
    df_representantes['Dia_nacimiento'] = pd.DatetimeIndex(df_representantes['date_of_birth']).day
    df_representantes = df_representantes[['representante_id', 'Anio_nacimiento', 'Mes_nacimiento', 'Dia_nacimiento', 'sede_id']]
    return df_representantes


def transform_ubicaciones(df_campus):
    df_ubicacion = df_campus[['id', 'province', 'city']].copy()
    df_ubicacion.rename(columns={'id': 'sede_id', 'province': 'Provincia', 'city': 'Ciudad'}, inplace=True)
    return df_ubicacion

def transform_fechas_creacion(df_campus):
    df_fecha_creacion = df_campus[['id', 'created_at']].copy()
    df_fecha_creacion['sede_id'] = df_fecha_creacion['id']
    df_fecha_creacion['Anio'] = pd.DatetimeIndex(df_fecha_creacion['created_at']).year
    df_fecha_creacion['Trimestre'] = pd.DatetimeIndex(df_fecha_creacion['created_at']).quarter
    df_fecha_creacion['Mes'] = pd.DatetimeIndex(df_fecha_creacion['created_at']).month
    df_fecha_creacion['Dia'] = pd.DatetimeIndex(df_fecha_creacion['created_at']).day
    df_fecha_creacion['Semana'] = pd.to_datetime(df_fecha_creacion['created_at']).dt.isocalendar().week
    df_fecha_creacion = df_fecha_creacion.rename(columns={'id': 'fecha_creacion_id'})
    df_fecha_creacion = df_fecha_creacion[['fecha_creacion_id', 'sede_id', 'Anio', 'Trimestre', 'Mes', 'Dia', 'Semana']]
    return df_fecha_creacion


def transfer_tipo_entidad():
    df_tipo_entidad = extract_table_to_df('tipo_entidad', engine_login_system)
    load_df_to_table(df_tipo_entidad, 'tipo_entidad', engine_datawarehouse, if_exists='replace')


def transform_entidades(df_obligatory_spaces, df_tipo_entidad):
    df_merged = pd.merge(df_obligatory_spaces, df_tipo_entidad, how='left', left_on='tipo_de_entidad', right_on='id')
    print("Columnas después de la fusión entidades: ", df_merged.columns.tolist())
    df_merged['provincia'] = df_obligatory_spaces['province']
    df_entidades = df_merged[['id_x', 'name', 'tipo_de_entidad_x', 'provincia']].rename(columns={'id_x': 'entidad_id', 'name': 'nombre'})
    return df_entidades

def transform_ubicacion_muertes_subitas(df_sudden_death, df_campus):
    df_ubicacion_muertes_subitas = pd.merge(
        df_sudden_death[['id', 'campus_id']],
        df_campus[['id', 'province', 'city']],
        left_on='campus_id',
        right_on='id',
        how='left'
    )
    df_ubicacion_muertes_subitas_final = df_ubicacion_muertes_subitas[['id_x', 'province', 'city']]
    df_ubicacion_muertes_subitas_final.rename(columns={
        'id_x': 'muerte_subita_id',
        'province': 'provincia',
        'city': 'ciudad'
    }, inplace=True)

    df_ubicacion_muertes_subitas_final.insert(0, 'id', range(1, len(df_ubicacion_muertes_subitas_final) + 1))
    return df_ubicacion_muertes_subitas_final


def transform_to_fact_table(df_sudden_death, df_campus, df_dea, df_fecha, df_ubicacion_muertes_subitas, df_sedes_muerte_subita):
    df_fact = pd.merge(df_sudden_death, df_fecha, left_on='id', right_on='muerte_subita_id', suffixes=('', '_fecha'))
    df_fact = pd.merge(df_fact, df_ubicacion_muertes_subitas, on='muerte_subita_id', suffixes=('', '_ubicacion'))
    df_fact = pd.merge(df_fact, df_sedes_muerte_subita, left_on='campus_id', right_on='muerte_subita_id', suffixes=('', '_sedes'))
    df_fact = pd.merge(df_fact, df_dea, left_on='dea_id', right_on='id', suffixes=('', '_dea'))
    
    df_fact_final = df_fact[['fecha_id', 'campus_id', 'id', 'dea_id']]
    df_fact_final.rename(columns={
    'fecha_id': 'id_fecha',
    'campus_id': 'id_sede',
    'id': 'id_muerte_subita',
    'dea_id': 'id_dea'
}, inplace=True)
    df_fact_final.insert(0, 'id', range(1, len(df_fact_final) + 1))
    return df_fact_final


def transform_deas(df_dea):
    df_deas = df_dea.rename(columns={'brand': 'marca', 'model': 'modelo'})
    df_deas = df_deas[['id', 'marca', 'modelo']]
    df_deas.rename(columns={'id': 'dea_id'}, inplace=True)
    return df_deas

def transform_sedes_muerte_subita(df_sudden_death, df_campus):
    df_sedes_muerte_subitas = pd.merge(
        df_sudden_death[['id', 'campus_id']],
        df_campus[['id', 'name']],
        left_on='campus_id',
        right_on='id',
        how='left'
    )

    df_sedes_muerte_subitas_final = df_sedes_muerte_subitas[['id_x', 'name']]
    df_sedes_muerte_subitas_final.rename(columns={
        'id_x': 'muerte_subita_id',
        'name': 'nombre_sede'
    }, inplace=True)
    
    df_sedes_muerte_subitas_final.insert(0, 'id', range(1, len(df_sedes_muerte_subitas_final) + 1))
    
    return df_sedes_muerte_subitas_final


def load_df_to_table(df, table_name, engine, if_exists='append'):
    print(df.head()) 
    print("Columnas del DataFrame:", df.columns.tolist()) 
    print("Tipos de datos del DataFrame:", df.dtypes) 
    df.to_sql(table_name, con=engine, if_exists=if_exists, index=False)


# Proceso ETL para cada conjunto de datos
def etl_process():
    df_status_description = extract_table_to_df('status_description', engine_login_system)
    df_campus = extract_table_to_df('campus', engine_login_system)
    df_campus_dea = extract_table_to_df('campus_dea', engine_login_system)
    df_users = extract_table_to_df('users', engine_login_system)
    df_obligatory_spaces = extract_table_to_df('obligatory_space', engine_login_system)
    df_tipo_entidad = extract_table_to_df('tipo_entidad', engine_login_system)
    df_dea = extract_table_to_df('dea', engine_login_system)  
    df_sudden_death = extract_table_to_df('sudden_death', engine_login_system)  
    df_campus_representatives = extract_table_to_df('campus_representatives', engine_login_system)
    df_transformed_with_relationships = extract_relationships()
    # Transformación
    df_sedes = transform_sedes(df_campus, df_campus_dea, df_status_description)
    df_ubicaciones = transform_ubicaciones(df_campus)
    df_fechas_creacion = transform_fechas_creacion(df_campus)
    df_entidades = transform_entidades(df_obligatory_spaces, df_tipo_entidad)
    df_deas = transform_deas(df_dea)
    df_campus_representatives = extract_table_to_df('campus_representatives', engine_login_system)
    df_representantes = transform_representantes(df_users, df_campus_representatives)
    df_sudden_death = transform_sudden_death(df_sudden_death, df_campus, df_dea)
    df_sudden_death = extract_table_to_df('sudden_death', engine_login_system)
    df_fecha = transform_fechas(df_sudden_death)
    df_dea_muerte_subita = transform_dea_muerte_subita(df_sudden_death, df_dea)
    df_ubicacion_muertes_subitas = transform_ubicacion_muertes_subitas(df_sudden_death, df_campus)
    df_sedes_muerte_subitas = transform_sedes_muerte_subita(df_sudden_death, df_campus)
    df_hechos = transform_to_fact_table(
        df_sudden_death, 
        df_campus, 
        df_dea, 
        df_fecha, 
        df_ubicacion_muertes_subitas, 
        df_sedes_muerte_subitas
    )
    
    # Carga
    load_df_to_table(df_sedes, 'sedes', engine_datawarehouse)
    load_df_to_table(df_representantes, 'representantes', engine_datawarehouse)
    load_df_to_table(df_ubicaciones, 'ubicaciones', engine_datawarehouse)
    load_df_to_table(df_fechas_creacion, 'fechas_creacion', engine_datawarehouse)
    load_df_to_table(df_entidades, 'entidades', engine_datawarehouse)
    load_df_to_table(df_deas, 'deas', engine_datawarehouse)
    load_df_to_table(df_transformed_with_relationships, 'indices', engine_datawarehouse)
    load_df_to_table(df_tipo_entidad, 'tipo_entidad', engine_datawarehouse, if_exists='replace')
    load_df_to_table(df_sudden_death, 'muertes_subitas', engine_datawarehouse)
    load_df_to_table(df_fecha, 'fecha', engine_datawarehouse)
    load_df_to_table(df_dea_muerte_subita, 'dea_muerte_subita', engine_datawarehouse)
    load_df_to_table(df_ubicacion_muertes_subitas, 'ubicacion_muertes_subitas', engine_datawarehouse)
    load_df_to_table(df_sedes_muerte_subitas, 'sedes_muerte_subita', engine_datawarehouse)
    load_df_to_table(df_hechos, 'hechos', engine_datawarehouse)




    

    query_indices = """
        INSERT INTO datawarehouse.indices (id_sede, id_entidad, id_representante, id_fecha_creacion, id_ubicacion, id_dea, id_tipo_entidad)
        SELECT 
            s.sede_id, 
            e.entidad_id, 
            r.representante_id, 
            fc.fecha_creacion_id, 
            u.sede_id,      
            d.dea_id,
            e.tipo_de_entidad_x
            
        FROM 
        datawarehouse.sedes s
        LEFT JOIN login_system.campus_of_obligatory_spaces coos ON s.sede_id = coos.campus_id
        LEFT JOIN datawarehouse.entidades e ON coos.obligatory_space_id = e.entidad_id
        LEFT JOIN datawarehouse.representantes r ON s.sede_id = r.sede_id  
        LEFT JOIN datawarehouse.fechas_creacion fc ON s.sede_id = fc.sede_id
        LEFT JOIN datawarehouse.ubicaciones u ON s.sede_id = u.sede_id
        LEFT JOIN login_system.campus_dea cd ON s.sede_id = cd.campus_id
        LEFT JOIN datawarehouse.tipo_entidad te on te.id = e.tipo_de_entidad_x
        LEFT JOIN datawarehouse.deas d ON cd.dea_id = d.dea_id;

        """

    with engine_datawarehouse.connect() as conn:
        conn.execute(text(query_indices))

if __name__ == '__main__':
    etl_process()
