Доброго времени суток,это игра Морской Бой!

Некоторые моменты по работе программы:
 - В начале программа ожидает либо аргументы командной строки либо ввод в диалоговом
окне.Аргументов командной строки должно быть 7: кол-во строк,затем количество столбцов,затем кол-во каждого корабля в порядке,указанном в тз(то есть carrier battleship cruiser destroyer submarine) и вводятся они через пробел(например 2 2 0 0 0 0 1).Если было введено меньшее кол-во аргументов или же они были введены в неверном(невозможном для корректной работы программы) формате (введено не число или отрицательное число,или размеры океана неположительны) то пользователю предлагается ввести параметры еще раз уже в диалоговом окне.
 - Если компьютер сгенерировал ваш океан то выводится сообщение об успешном создании и сам океан (в самом начале выводятся ячейки океана с надписью "not-fired",как сказано в тз).
 - Далее пользователю предлагается ввести кол-во торпед и включить режим восстановления корабля(доп функционал в соответствии с тз).
 - Далее пользователю предлагается ввести номер строки и столбца(через пробел или построчно),в которую он хочет попасть.Если у пользователя есть торпеда,которой он хочет воспользоваться,он должен ввести префикс T,далее пробел или перенос строки и номер строки и столбца через пробел или построчно.
 - Программа выводит сообщение об успешном попадании,промахе или потоплении корабля с информацией о нем.
 - Пользователю предлагается ввести номер строки и столбца пока все корабли не потоплены.
 - После каждого выстрела программа выводит океан(по условию тз).
 - Реализован основной и доп функционал программы,код задокументирован на английском языке с комментариями javadoc а также простыми комментариями в местах требующий уточнения,программа также выводит информацию и сообщения на английским языке. 