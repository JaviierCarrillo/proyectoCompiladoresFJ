package co.edu.uniquindio.compiladores.lexico

class AnalizadorLexico(var codigoFuente:String) {

    var posicionActual = 0
    var caracterActual = codigoFuente[0]
    var listaTokens = ArrayList<Token>()
    var finCodigo = 0.toChar()
    var filaActual = 0
    var columnaActual = 0


    fun almacenarToken(lexema:String, categoria: Categoria, fila:Int, columna:Int) = listaTokens.add(Token(lexema, categoria, fila, columna))

    fun hacerBT(posicionInicial:Int, filaInicial:Int, columnaInicial:Int){
        posicionActual = posicionInicial
        filaActual = filaInicial
        columnaActual = filaInicial
        caracterActual = codigoFuente[posicionActual]
    }

    fun analizar(){
        while(caracterActual != finCodigo){

            if (caracterActual == ' ' || caracterActual == '\t' || caracterActual == '\n'){
                obtenerSiguienteCaracter()
                continue
            }

            if (esEntero()) continue
            if (esDecimal()) continue
            almacenarToken(""+caracterActual, Categoria.DESCONOCIDO, filaActual, columnaActual)
            obtenerSiguienteCaracter()

        }
    }

    fun esDecimal():Boolean{
        if (caracterActual == '.' || caracterActual.isDigit()){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual

            if (caracterActual == '.'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual.isDigit()){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                }
            }else{
                lexema += caracterActual
                obtenerSiguienteCaracter()

                while (caracterActual.isDigit()){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
                if (caracterActual == '.') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }

            }
            while (caracterActual.isDigit()){
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }
            almacenarToken(lexema, Categoria.DECIMAL, filaInicial, columnaInicial)
            return true

        }
        return false
    }

    fun esEntero():Boolean{
        if (caracterActual.isDigit()){

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (caracterActual.isDigit()){
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }

            if (caracterActual == '.'){
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }

            almacenarToken(lexema, Categoria.ENTERO, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    fun obtenerSiguienteCaracter(){
        if (posicionActual == codigoFuente.length-1){
            caracterActual = finCodigo
        }else{

            if (caracterActual == '\n'){
                filaActual++
                columnaActual = 0
            }else{
                columnaActual++
            }

            posicionActual++
            caracterActual = codigoFuente[posicionActual]
        }
    }
}