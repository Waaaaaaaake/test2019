text = input("Type message (See format in file.txt): ")

# Оставляем в файле только буквы в нижнем регистре
b_final = text.lower();
arr_letters = []
arr_numbers = []

# убираем всё лишнее из массива
for value in range(0,192):
    b_final = b_final.replace(chr(value), "")

# print(b_final)

# инициализируем первый элемент, чтобы цикл заработал
# первая буква будет считаться два раза

arr_letters.append(b_final[0])
arr_numbers.append(0)

# посчитали количество букв во всём тексте. Записали в соответствующий массив

for value in range(0, len(b_final)):
    for it in range(0, len(arr_letters)):
        if b_final[value] == arr_letters[it]:
            arr_numbers[arr_letters.index(b_final[value])] +=1
            break
        elif it+1 == (len(arr_letters)):
            arr_letters.append(b_final[value])
            arr_numbers.append(1)
            break

# Вычислили индекс элемента, который встречался чаще всего
max_index = 0
for it in range(1, len(arr_numbers)):
    if arr_numbers[max_index] < arr_numbers[it]:
        max_index = it

# ascii 1086
# print (ord("о"))

a = "о"
# 238
max_takt = ord(a)
max_text_takt = ord(arr_letters[max_index])

# Посчитали сдвиг
shift = max_takt-max_text_takt

print(shift)

# делаем буква + shift

#192 windows-1251
#1040
high_letter_left = "А"

#223 windows-1251
#1071
high_letter_right = "Я"

#224 windows-1251
#1072
low_letter_left = "а"

#255 windows-1251
#1103
low_letter_right = "я"

# print(ord('А'))
# print(ord('Я'))
# print(ord('а'))
# print(ord('я'))


for value in range(0, len(text)):
    # Получили код очередной буквы массива
    uncode = ord(text[value])
    # Если буква
    if(uncode > 1039) and (uncode < 1104):
        # Если большая буква
        if uncode < 1072:
            # Если в сдвиге передвинулась за код 'А'
            if ((shift < 0) and (uncode + shift)) < 1040:
                more_shift = shift + (uncode - ord(high_letter_left))
                text = text[:value] + chr((ord(high_letter_right) + 1) + more_shift) + text[value+1:]
                break
            # Если в сдвиге передвинулась за код 'Я'
            elif ((shift > 0) and (uncode + shift)) > 1071:
                more_shift = shift - (ord(high_letter_right) - uncode)
                text = text[:value] + chr((high_letter_left - 1) + more_shift) + text[value+1:]
                break
            # Если сдвига хватает
            else:
                text = text[:value] + chr(uncode - shift) + text[value+1:]
                break
        # Если буква маленькая
        else:
            # Если в сдвиге передвинулась за код 'a'
            if (shift < 0) and (uncode + shift) < 1072:
                more_shift = shift + (uncode - ord(low_letter_left))
                text = text[:value] + chr((ord(low_letter_right + 1)) + more_shift) + text[value+1:]
                break
            # Если в сдвиге передвинулась за код 'я'
            elif (shift > 0) and (uncode + shift) > 1103:
                more_shift = shift - (ord(low_letter_right) - uncode)
                text = text[:value] + chr((ord(low_letter_left) - 1) + more_shift) + text[value+1:]
                break
            # Если сдвига хватает
            else:
                text = text[:value] + chr(uncode - shift) + text[value+1:]
                break

print(text)
