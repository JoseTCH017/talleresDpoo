package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ComboTest
{
    private Combo combo1;
    private Combo comboSinDescuento;
    private ProductoMenu item1;
    private ProductoMenu item2;
    private ProductoMenu item3;

    @BeforeEach
    void setUp( ) throws Exception
    {
        item1 = new ProductoMenu( "corral", 14000 );
        item2 = new ProductoMenu( "papas medianas", 5500 );
        item3 = new ProductoMenu( "gaseosa", 5000 );

        ArrayList<ProductoMenu> items = new ArrayList<>( );
        items.add( item1 );
        items.add( item2 );
        items.add( item3 );

        // Suma de precios = 14000 + 5500 + 5000 = 24500
        // Descuento del 10% (0.10) => Precio = 24500 * 0.9 = 22050
        combo1 = new Combo( "combo corral", 0.10, items );
        comboSinDescuento = new Combo( "combo normal", 0.0, items );
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
    }

    @Test
    void testGetNombre( )
    {
        assertEquals( "combo corral", combo1.getNombre( ), "El nombre del combo no es el esperado." );
        assertEquals( "combo normal", comboSinDescuento.getNombre( ), "El nombre del combo no es el esperado." );
    }

    @Test
    void testGetPrecioConDescuento( )
    {
        // 24500 * (1 - 0.10) = 22050
        assertEquals( 22050, combo1.getPrecio( ), "El precio del combo con descuento del 10% no es el esperado." );
    }

    @Test
    void testGetPrecioSinDescuento( )
    {
        // 24500 * (1 - 0.0) = 24500
        assertEquals( 24500, comboSinDescuento.getPrecio( ), "El precio del combo sin descuento debe ser igual a la suma de sus productos." );
    }

    @Test
    void testGetPrecioConDescuentoDecimal( )
    {
        ArrayList<ProductoMenu> items = new ArrayList<>( );
        items.add( new ProductoMenu( "especial", 24000 ) );
        items.add( new ProductoMenu( "papas medianas", 5500 ) );
        items.add( new ProductoMenu( "gaseosa", 5000 ) );
        // Suma = 34500. Descuento 9.5% (0.095) => 34500 * (1 - 0.095) = 34500 * 0.905 = 31222.5 => (int) 31222
        Combo comboEspecial = new Combo( "combo especial", 0.095, items );
        assertEquals( ( int ) ( 34500 * ( 1 - 0.095 ) ), comboEspecial.getPrecio( ), "El precio del combo con descuento del 9.5% no es el esperado." );
    }

    @Test
    void testGenerarTextoFactura( )
    {
        String esperado = "Combo combo corral\n" +
                          " Descuento: 0.1\n" +
                          "            22050\n";
        assertEquals( esperado, combo1.generarTextoFactura( ), "El texto de la factura del combo no coincide con el formato esperado." );
    }
}
