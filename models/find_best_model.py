import os

PATH = 'train_1'

def find_high_score_file(dir_path):
    high_score = 0
    min_generation = 0
    result = ''
    for dp, dn, filenames in os.walk(dir_path):
        for f in filenames:
            splited = f.split('_')
            score = int(splited[-1])
            generation = int(splited[-2])
            if score >= high_score:
                if score > high_score or generation < min_generation:
                    high_score = score
                    min_generation = generation
                    result = os.path.join(dp, f)
    return result, high_score, min_generation

def list_dir(dir_path, max_generation=-1):
    files = []
    for dp, dn, filenames in os.walk(dir_path):
        for f in filenames:
            splited = f.split('_')
            generation = int(splited[-2])
            if max_generation == -1 or generation <= max_generation:
                files.append('"' + os.path.join(dp, f) + '"')

    files.sort(key=lambda f: int(f.split('_')[-2]))
    return '{' + ', '.join(files) + '}'


def main():
    full_path, high_score, min_generation = find_high_score_file(PATH)
    dir_path = os.path.dirname(full_path)
    for_java = list_dir(dir_path, min_generation)
    print(for_java)

if __name__ == '__main__':
    main()
